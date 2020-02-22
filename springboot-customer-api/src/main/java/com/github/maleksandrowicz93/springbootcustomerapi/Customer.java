package com.github.maleksandrowicz93.springbootcustomerapi;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter @Setter @NoArgsConstructor
public class Customer {

    private Integer creditId;
    private String firstName;
    private String pesel;
    private String surname;

}
