package com.github.maleksandrowicz93.springbootcreditapi;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CreditService {

    int createCredit(CreditApplicationDto creditApplicationDto);
    List<CreditApplicationDto> getCreditReport();

}
