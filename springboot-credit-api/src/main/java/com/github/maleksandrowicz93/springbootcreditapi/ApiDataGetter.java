package com.github.maleksandrowicz93.springbootcreditapi;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

@Log4j
@Component
public class ApiDataGetter {

    @Value("${CUSTOMER_API}")
    private String CUSTOMER_API;
    @Value("${PRODUCT_API}")
    private String PRODUCT_API;

    @Autowired
    private RestService restService;

    public List<CustomerDto> getCustomers() {
        String customerUrl = "http://" + CUSTOMER_API + ":8010/customer";
        log.info("Sending request for get customers...");
        CustomerDto[] customerDtos = (CustomerDto[]) restService.getResponseBody(customerUrl, CustomerDto[].class);
        return  (customerDtos != null) ? Arrays.asList(customerDtos) : new ArrayList<>();
    }

    public List<ProductDto> getProducts() {
        String productUrl = "http://" + PRODUCT_API + ":8020/product";
        log.info("Sending request for get products...");
        ProductDto[] productDtos = (ProductDto[]) restService.getResponseBody(productUrl, ProductDto[].class);
        return  (productDtos != null) ? Arrays.asList(productDtos) : new ArrayList<>();
    }

}
