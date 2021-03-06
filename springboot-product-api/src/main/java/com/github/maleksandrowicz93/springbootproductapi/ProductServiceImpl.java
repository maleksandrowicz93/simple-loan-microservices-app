package com.github.maleksandrowicz93.springbootproductapi;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Log4j
@Component
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepo productRepo;

    @Override
    public List<Product> getProducts(List<Integer> creditIds) {
        log.info("Getting products...");
        List<Product> allProducts = productRepo.findAll();
        return allProducts.stream()
                .filter(c -> creditIds.contains(c.getCreditId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> convertFromProductList(List<Product> products) {
        log.info("Converting Product list to ProductDto one...");
        return products.stream()
                .sorted(Comparator.comparing(Product::getCreditId))
                .map(this::convertFromProduct)
                .collect(Collectors.toList());
    }

    private ProductDto convertFromProduct(Product product) {
        log.info("Converting Product instance to ProductDto one...");
        ProductDto productDto = new ProductDto();
        productDto.setProductName(product.getProductName());
        productDto.setValue(product.getValue());
        return productDto;
    }

    @Override
    public Product convertFromDto(ProductDto productDto) {
        log.info("Converting ProductDto instance to Product one...");
        Product product = new Product();
        product.setProductName(productDto.getProductName());
        product.setValue(productDto.getValue());
        return product;
    }

    @Override
    public void saveProduct(Product product) {
        log.info("Adding new product...");
        productRepo.save(product);
    }
}
