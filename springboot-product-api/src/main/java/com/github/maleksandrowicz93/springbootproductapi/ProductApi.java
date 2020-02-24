package com.github.maleksandrowicz93.springbootproductapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductApi {

    @Autowired
    private ProductService productService;

    private List<Integer> creditIds = new ArrayList<>();

    @GetMapping()
    public List<ProductDto> GetProducts() {
        List<Product> products = productService.getProducts(creditIds);
        return productService.convertFromProductList(products);
    }

    @PostMapping()
    public void CreateProduct(@RequestBody ProductDto productDto) {
        Product product = productService.convertFromDto(productDto);
        productService.saveProduct(product);
    }

    @PostMapping("/ids")
    private void setCreditIds(@RequestBody Integer[] ids) {
        creditIds = Arrays.asList(ids);
    }

}
