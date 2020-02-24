package com.github.maleksandrowicz93.springbootcreditapi;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class ProductDto {

    private int creditId;
    private ProductEnum productName;
    private Integer value;

    public ProductDto(ProductEnum productName, Integer value) {
        this.productName = productName;
        this.value = value;
    }
}
