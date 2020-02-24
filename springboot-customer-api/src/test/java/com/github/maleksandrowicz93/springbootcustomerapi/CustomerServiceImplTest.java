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
        expectedCustomer.setCreditId(1);
        customerDBImage.addAll(prepareMockData());
    }

    private List<Customer> prepareMockData() {
        List<Customer> customers = new ArrayList<>();
        Customer customer1 = new Customer(1, "John", "Pesci", "11111111111");
        customers.add(customer1);
        Customer customer2 = new Customer(2,"Joaquin", "Phoenix", "22222222222");
        customers.add(customer2);
        Customer customer3 = new Customer(2,"Rami", "Malek", "33333333333");
        customers.add(customer3);
        Customer customer4 = new Customer(3, "Robert", "Lewandowski", "44444444444");
        customers.add(customer4);
        return customers;
    }

    @Test
    void save_customer() {
        //given
        when(customerRepo.save(any(Customer.class))).thenAnswer(invocationOnMock ->  {
            customerDBImage.add(expectedCustomer);
            return expectedCustomer;
        });
        //when
        customerService.saveCustomer(new Customer());
        //then
        assertTrue(customerDBImage.contains(expectedCustomer));
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
                expectedCustomer.getPesel(),
                expectedCustomer.getCreditId()
        );
        //when
        Customer converted = customerService.convertFromDto(toConvert);
        //then
        assertTrue(expectedCustomer.getFirstName().equals(converted.getFirstName())
                && expectedCustomer.getSurname().equals(converted.getSurname())
                && expectedCustomer.getPesel().equals(converted.getPesel())
                && expectedCustomer.getCreditId().intValue() == converted.getCreditId().intValue());
    }

    @Test
    void convert_from_customer_list() {
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
                    && customerDto.getPesel().equals(c.getPesel())
                    && customerDto.getCreditId() == c.getCreditId());
        }
    }

}