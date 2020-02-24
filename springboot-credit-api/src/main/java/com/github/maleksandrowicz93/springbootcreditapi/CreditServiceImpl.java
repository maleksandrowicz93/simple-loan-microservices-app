package com.github.maleksandrowicz93.springbootcreditapi;

import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Log4j
@Component
public class CreditServiceImpl implements CreditService {

    @Override
    public Credit createCreditFromApplication(CreditApplicationDto creditApplicationDto) {
        return null;
    }

    @Override
    public List<Credit> getCredits() {
        return null;
    }

    @Override
    public List<Integer> getCreditIds(List<Credit> credits) {
        return null;
    }

    @Override
    public List<CreditApplicationDto> createCreditReport(
            List<Credit> credits,
            List<CustomerDto> customerDtoList,
            List<ProductDto> productDtoList
    ) {
        return null;
    }

}
