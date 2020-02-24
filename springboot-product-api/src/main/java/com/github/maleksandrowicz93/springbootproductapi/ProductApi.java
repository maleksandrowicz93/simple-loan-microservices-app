package com.github.maleksandrowicz93.springbootproductapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductApi {

    @Autowired
    private ProductService productService;

    @GetMapping()
    public List<ProductDto> GetProducts(@RequestBody List<Integer> creditIds) {
        List<Product> products = productService.getProducts(creditIds);
        return productService.convertFromProductList(products);
    }

    @PostMapping()
    public void CreateProduct(@RequestBody ProductDto productDto) {
        Product product = productService.convertFromDto(productDto);
        productService.saveProduct(product);
    }

}
