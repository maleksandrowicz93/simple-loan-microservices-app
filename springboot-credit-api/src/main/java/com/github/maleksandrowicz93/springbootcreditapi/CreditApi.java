package com.github.maleksandrowicz93.springbootcreditapi;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/credit")
public class CreditApi {

    @PostMapping
    public int CreateCredit(@RequestBody CreditApplicationDto creditApplicationDto) {
        return 0;
    }

    @GetMapping
    public List<CreditApplicationDto> GetCredits() {
        return null;
    }
    
}
