package com.github.maleksandrowicz93.springbootcreditapi;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CreditService {

    Credit createCreditFromApplication(CreditApplicationDto creditApplicationDto);
    List<Credit> getCredits();
    List<Integer> getCreditIds(List<Credit> credits);
    List<CreditApplicationDto> createCreditApplicationList(
            List<Credit> credits,
            List<CustomerDto> customerDtoList,
            List<ProductDto> productDtoList
    );
}
