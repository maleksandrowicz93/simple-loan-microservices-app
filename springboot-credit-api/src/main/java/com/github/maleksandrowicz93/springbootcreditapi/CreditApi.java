package com.github.maleksandrowicz93.springbootcreditapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        Credit credit = creditService.createCreditFromApplication(creditApplicationDto);
        ProductDto productDto = creditApplicationDto.getProductDto();
        restTemplate.postForObject("http://" + HOST_NAME + ":8020/product", productDto, ProductDto.class);
        CustomerDto customerDto = creditApplicationDto.getCustomerDto();
        restTemplate.postForObject("http://" + HOST_NAME + ":8010/customer", customerDto, CustomerDto.class);
        return 0;
    }

    @GetMapping
    public List<CreditApplicationDto> GetCredits() {
        List<Credit> credits = creditService.getCredits();
        List<CreditApplicationDto> creditReport = new ArrayList<>();
        if (credits != null && !credits.isEmpty()) {
            List<Integer> creditIds = creditService.getCreditIds(credits);
            ResponseEntity<CustomerDto[]> GetCustomersExchange = restTemplate.exchange(
                    "http://" + HOST_NAME + ":8010/customer",
                    HttpMethod.GET,
                    HttpEntity.EMPTY,
                    CustomerDto[].class,
                    creditIds
            );
            List<CustomerDto> customerDtoList = Arrays.asList(GetCustomersExchange.getBody());
            ResponseEntity<ProductDto[]> GetProductsExchange = restTemplate.exchange(
                    "http://" + HOST_NAME + ":8020/product",
                    HttpMethod.GET,
                    HttpEntity.EMPTY,
                    ProductDto[].class,
                    creditIds
            );
            List<ProductDto> productDtoList = Arrays.asList(GetProductsExchange.getBody());
            creditReport.addAll(creditService.createCreditApplicationList(credits, customerDtoList, productDtoList));
        }
        return creditReport;
    }

}
