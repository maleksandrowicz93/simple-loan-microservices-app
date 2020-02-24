package com.github.maleksandrowicz93.springbootproductapi;

import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Log4j
@Component
public class ProductServiceImpl implements ProductService {

    @Override
    public List<Product> getProducts(List<Integer> creditIds) {
        return null;
    }

    @Override
    public List<ProductDto> convertFromProductList(List<Product> products) {
        return null;
    }

    @Override
    public Product convertFromDto(ProductDto productDto) {
        return null;
    }

    @Override
    public void saveProduct(Product product) {
    }
}
