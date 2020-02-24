package com.github.maleksandrowicz93.springbootproductapi;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {

    List<Product> getProducts(List<Integer> creditIds);
    List<ProductDto> convertFromProductList(List<Product> products);
    Product convertFromDto(ProductDto productDto);
    void saveProduct(Product product);

}
