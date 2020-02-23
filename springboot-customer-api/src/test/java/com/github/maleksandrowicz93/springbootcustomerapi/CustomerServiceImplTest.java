package com.github.maleksandrowicz93.springbootcustomerapi;

import lombok.extern.log4j.Log4j;
import org.apache.log4j.BasicConfigurator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Log4j
@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private CustomerRepo customerRepo;
    @InjectMocks
    private CustomerServiceImpl customerService;

    private Customer expectedCustomer = new Customer();
    private List<Customer> customerDBImage = new ArrayList<>();

    @BeforeAll
    static void beforeTests() {
        BasicConfigurator.configure();
        log.info("Starting CustomerServiceImpl tests...");
    }

    @BeforeEach
    void setup() {
        expectedCustomer.setFirstName("John");
        expectedCustomer.setSurname("Brennox");
        expectedCustomer.setPesel("00000000000");
        customerDBImage.addAll(prepareMockData());
    }

    private List<Customer> prepareMockData() {
        List<Customer> customers = new ArrayList<>();
        Customer customer1 = new Customer("John", "Pesci", "11111111111");
        customer1.setCreditId(1);
        customers.add(customer1);
        Customer customer2 = new Customer("Joaquin", "Phoenix", "22222222222");
        customer2.setCreditId(2);
        customers.add(customer2);
        Customer customer3 = new Customer("Rami", "Malek", "33333333333");
        customer3.setCreditId(2);
        customers.add(customer3);
        Customer customer4 = new Customer("Robert", "Lewandowski", "44444444444");
        customer4.setCreditId(3);
        customers.add(customer4);
        return customers;
    }

    @Test
    void save_customer() {
        //given
        mockSaveCustomer();
        //when
        customerService.saveCustomer(new Customer());
        //then
        assertTrue(customerDBImage.contains(expectedCustomer));
    }

    private void mockSaveCustomer() {
        when(customerRepo.save(any(Customer.class))).thenAnswer(invocationOnMock ->  {
            customerDBImage.add(expectedCustomer);
            return expectedCustomer;
        });
    }

    @Test
    void save_existing_customer() {
        //given
        mockSaveCustomer();
        //when
        customerService.saveCustomer(new Customer());
        customerService.saveCustomer(new Customer());
        //then
        assertThrows(SQLIntegrityConstraintViolationException.class, () -> {
            if (customerDBImage.get(customerDBImage.size()-1) == customerDBImage.get(customerDBImage.size()-2))
                throw new SQLIntegrityConstraintViolationException();
        });
    }

    @Test
    void get_customers() {
        //given
        List<Integer> ids = Arrays.asList(1, 2);
        when(customerRepo.findAll()).thenReturn(prepareMockData());
        //when
        List<Customer> customers = customerService.getCustomers(ids);
        //then
        assertEquals(3, customers.size());
    }

    @Test
    void convert_from_dto() {
        //given
        CustomerDto toConvert = new CustomerDto(
                expectedCustomer.getFirstName(),
                expectedCustomer.getSurname(),
                expectedCustomer.getPesel()
        );
        //when
        Customer converted = customerService.convertFromDto(toConvert);
        //then
        assertTrue(expectedCustomer.getFirstName().equals(converted.getFirstName())
                && expectedCustomer.getSurname().equals(converted.getSurname())
                && expectedCustomer.getPesel().equals(converted.getPesel()));
    }

    @Test
    void convertFromCustomerList() {
        //given
        List<Customer> customers = prepareMockData();
        //when
        List<CustomerDto> customerDtoList = customerService.convertFromCustomerList(customers);
        //then
        assertEquals(customers.size(), customerDtoList.size());
        for (int i = 0; i < customers.size(); i++) {
            Customer c = customers.get(i);
            CustomerDto customerDto = customerDtoList.get(i);
            assertTrue(customerDto.getFirstName().equals(c.getFirstName())
                    && customerDto.getSurname().equals(c.getSurname())
                    && customerDto.getPesel().equals(c.getPesel()));
        }
    }

}