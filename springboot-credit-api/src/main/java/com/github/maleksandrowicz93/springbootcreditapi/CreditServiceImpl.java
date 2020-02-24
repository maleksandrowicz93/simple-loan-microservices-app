package com.github.maleksandrowicz93.springbootcreditapi;

import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;

@Log4j
@Component
public class CreditServiceImpl implements CreditService {

    @Override
    public Credit createCreditFromApplication(CreditApplicationDto creditApplicationDto) {
        return null;
    }

}
