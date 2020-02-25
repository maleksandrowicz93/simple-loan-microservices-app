package com.github.maleksandrowicz93.springbootcreditapi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CustomerDto {

    private String firstName;
    private String surname;
    private String pesel;

}
