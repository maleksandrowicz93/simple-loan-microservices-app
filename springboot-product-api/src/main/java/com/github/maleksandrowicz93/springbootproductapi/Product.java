package com.github.maleksandrowicz93.springbootproductapi;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter @NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer creditId;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductEnum productName;
    @Column(nullable = false)
    private Integer value;

    public Product(Integer creditId, ProductEnum productName, Integer value) {
        this.creditId = creditId;
        this.productName = productName;
        this.value = value;
    }
}
