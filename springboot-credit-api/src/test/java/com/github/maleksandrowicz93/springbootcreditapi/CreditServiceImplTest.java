package com.github.maleksandrowicz93.springbootcreditapi;

import lombok.extern.log4j.Log4j;
import org.apache.log4j.BasicConfigurator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@Log4j
@ExtendWith(MockitoExtension.class)
class CreditServiceImplTest {

    @Mock
    private CreditRepo creditRepo;
    @Mock
    private ApiDataGetter apiDataGetter;
    @InjectMocks
    private CreditServiceImpl creditService;

    private Credit expectedCredit = new Credit();
    private List<Credit> creditDBImage = new ArrayList<>();

    @BeforeAll
    static void beforeTests() {
        BasicConfigurator.configure();
        log.info("Starting CustomerServiceImpl tests...");
    }

    @BeforeEach
    void setup() {
        expectedCredit.setCreditName("sample_Credit");
        creditDBImage.addAll(prepareMockData());
        lenient().when(creditRepo.findAll()).thenReturn(creditDBImage);
    }

    private List<Credit> prepareMockData() {
        List<Credit> credits = new ArrayList<>();
        Credit credit1 = new Credit("credit1");
        credit1.setId(1);
        credits.add(credit1);
        Credit credit2 = new Credit("credit2");
        credit2.setId(2);
        credits.add(credit2);
        Credit credit3 = new Credit("credit3");
        credit3.setId(3);
        credits.add(credit3);
        Credit credit4 = new Credit("credit4");
        credit4.setId(4);
        credits.add(credit4);
        return credits;
    }

    @Test
    void createCredit() {
        //given
        int newCreditId = 100;
        when(creditRepo.save(any(Credit.class))).thenAnswer(invocationOnMock ->  {
            expectedCredit.setId(newCreditId);
            creditDBImage.add(expectedCredit);
            return expectedCredit;
        });
        CreditApplicationDto creditApplicationDto = new CreditApplicationDto();
        creditApplicationDto.setCreditName(expectedCredit.getCreditName());
        creditApplicationDto.setProductDto(new ProductDto(ProductEnum.CONSUMER, 2000));
        creditApplicationDto.setCustomerDto(new CustomerDto("new", "customer", "99999999999"));
        //when
        int creditId = creditService.createCredit(creditApplicationDto);
        //then
        assertEquals(newCreditId, creditId);
        assertEquals(prepareMockData().size()+1, creditDBImage.size());
    }

    @Test
    void getCreditReport() {
        //given
        List<Credit> credits = creditDBImage;
        List<ProductDto> productDtoList = prepareMockProductData();
        List<CustomerDto> customerDtoList = prepareMockCustomerData();
        when(apiDataGetter.getCustomers()).thenReturn(prepareMockCustomerData());
        when(apiDataGetter.getProducts()).thenReturn(prepareMockProductData());
        //when
        List<CreditApplicationDto> report = creditService.getCreditReport();
        //then
        assertEquals(credits.size(), report.size());
        assertEquals(customerDtoList.size(), report.size());
        assertEquals(productDtoList.size(), report.size());
    }

    private List<CustomerDto> prepareMockCustomerData() {
        List<CustomerDto> customerDtoList = new ArrayList<>();
        CustomerDto customerDto1 = new CustomerDto("name1", "surname1", "00000000001");
        customerDto1.setCreditId(1);
        customerDtoList.add(customerDto1);
        CustomerDto customerDto2 = new CustomerDto("name2", "surname2", "00000000002");
        customerDto2.setCreditId(2);
        customerDtoList.add(customerDto2);
        CustomerDto customerDto3 = new CustomerDto("name3", "surname3", "00000000003");
        customerDto3.setCreditId(3);
        customerDtoList.add(customerDto3);
        CustomerDto customerDto4 = new CustomerDto("name4", "surname4", "00000000004");
        customerDto4.setCreditId(4);
        customerDtoList.add(customerDto4);
        return customerDtoList;
    }

    private List<ProductDto> prepareMockProductData() {
        List<ProductDto> productDtoList = new ArrayList<>();
        ProductDto productDto1 = new ProductDto(ProductEnum.CONSUMER, 1000);
        productDto1.setCreditId(1);
        productDtoList.add(productDto1);
        ProductDto productDto2 = new ProductDto(ProductEnum.INVESTMENT, 20000);
        productDto2.setCreditId(2);
        productDtoList.add(productDto2);
        ProductDto productDto3 = new ProductDto(ProductEnum.MORTGAGE, 300000);
        productDto3.setCreditId(3);
        productDtoList.add(productDto3);
        ProductDto productDto4 = new ProductDto(ProductEnum.CONSOLIDATION, 400);
        productDto4.setCreditId(4);
        productDtoList.add(productDto4);
        return productDtoList;
    }

}