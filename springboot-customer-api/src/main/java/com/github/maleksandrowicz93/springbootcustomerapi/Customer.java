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

    @Column(nullable = false)
    private Integer creditId;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String surname;
    @Column(nullable = false, length = 11)
    private String pesel;

    public Customer(Integer creditId, String firstName, String surname, String pesel) {
        this.creditId = creditId;
        this.firstName = firstName;
        this.surname = surname;
        this.pesel = pesel;
    }
}
