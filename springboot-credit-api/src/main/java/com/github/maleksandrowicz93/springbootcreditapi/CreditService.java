package com.github.maleksandrowicz93.springbootcreditapi;

import org.springframework.stereotype.Service;

@Service
public interface CreditService {

    Credit createCreditFromApplication(CreditApplicationDto creditApplicationDto);

}
