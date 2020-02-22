package com.github.maleksandrowicz93.springbootcustomerapi;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter @NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer creditId;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String surname;
    @Column(nullable = false, unique = true, length = 13)
    private String pesel;

    public Customer(String firstName, String surname, String pesel) {
        this.firstName = firstName;
        this.surname = surname;
        this.pesel = pesel;
    }
}
