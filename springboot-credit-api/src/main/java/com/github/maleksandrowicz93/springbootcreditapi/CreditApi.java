package com.github.maleksandrowicz93.springbootcreditapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Log4j
@RestController
@RequestMapping("/credit")
public class CreditApi {

    @Value("${HOST_NAME}")
    private String HOST_NAME;

    @Autowired
    private CreditService creditService;
    @Autowired
    private RestTemplate restTemplate;

    @PostMapping
    public int CreateCredit(@RequestBody CreditApplicationDto creditApplicationDto) {
        int creditId = creditService.createCreditFromApplication(creditApplicationDto).getId();
        ProductDto productDto = creditApplicationDto.getProductDto();
        productDto.setCreditId(creditId);
        restTemplate.postForObject("http://" + HOST_NAME + ":8020/product", productDto, ProductDto.class);
        CustomerDto customerDto = creditApplicationDto.getCustomerDto();
        customerDto.setCreditId(creditId);
        restTemplate.postForObject("http://" + HOST_NAME + ":8010/customer", customerDto, CustomerDto.class);
        return creditId;
    }

    @GetMapping
    public List<CreditApplicationDto> GetCredits() throws JsonProcessingException {

        List<Credit> credits = creditService.getCredits();
        List<Integer> creditIds = creditService.getCreditIds(credits);
        restTemplate.postForObject("http://" + HOST_NAME + ":8010/customer/ids", creditIds.toArray(), Object[].class);
        restTemplate.postForObject("http://" + HOST_NAME + ":8020/product/ids", creditIds.toArray(), Object[].class);
        List<CreditApplicationDto> creditReport = new ArrayList<>();

        if (credits != null && !credits.isEmpty()) {

            ResponseEntity<CustomerDto[]> GetCustomersExchange = restTemplate.exchange(
                    "http://" + HOST_NAME + ":8010/customer",
                    HttpMethod.GET,
                    HttpEntity.EMPTY,
                    CustomerDto[].class
            );
            CustomerDto[] customerDtos = GetCustomersExchange.getBody();

            List<CustomerDto> customerDtoList = (customerDtos != null) ? Arrays.asList(customerDtos) : new ArrayList<>();
            ResponseEntity<ProductDto[]> GetProductsExchange = restTemplate.exchange(
                    "http://" + HOST_NAME + ":8020/product",
                    HttpMethod.GET,
                    HttpEntity.EMPTY,
                    ProductDto[].class
            );
            ProductDto[] productDtos = GetProductsExchange.getBody();
            List<ProductDto> productDtoList = (productDtos != null) ? Arrays.asList(productDtos) : new ArrayList<>();
            creditReport.addAll(creditService.createCreditReport(credits, customerDtoList, productDtoList));

        }

        return creditReport;

    }

}
