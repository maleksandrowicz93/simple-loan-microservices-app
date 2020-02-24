package com.github.maleksandrowicz93.springbootcreditapi;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class CustomerDto {

    private String firstName;
    private String surname;
    private String pesel;
    private int creditId;

    public CustomerDto(String firstName, String surname, String pesel) {
        this.firstName = firstName;
        this.surname = surname;
        this.pesel = pesel;
    }
}
