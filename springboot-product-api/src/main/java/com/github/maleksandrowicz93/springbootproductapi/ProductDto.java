package com.github.maleksandrowicz93.springbootproductapi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ProductDto {

    private int creditId;
    private ProductEnum productName;
    private Integer value;

}
