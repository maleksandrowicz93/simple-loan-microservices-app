package com.github.maleksandrowicz93.springbootcreditapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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
        return null;
    }

}
