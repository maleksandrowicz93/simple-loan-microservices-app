package com.github.maleksandrowicz93.springbootcreditapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/credit")
public class CreditApi {

    @Autowired
    private CreditService creditService;

    @PostMapping
    public int CreateCredit(@RequestBody CreditApplicationDto creditApplicationDto) {
        return creditService.createCredit(creditApplicationDto);
    }

    @GetMapping
    public List<CreditApplicationDto> GetCredits() throws JsonProcessingException {
        return creditService.getCreditReport();
    }

}
