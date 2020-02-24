package com.github.maleksandrowicz93.springbootcreditapi;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class CreditApplicationDto {

    private CustomerDto customerDto;
    private ProductDto productDto;
    private String creditName;

}
