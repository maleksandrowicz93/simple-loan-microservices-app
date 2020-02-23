package com.github.maleksandrowicz93.springbootproductapi;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductApi {

    @GetMapping("/customer")
    public List<ProductDto> GetProducts(@RequestBody List<Integer> creditIds) {
        return null;
    }

    @PostMapping("/customer")
    public void CreateProduct(@RequestBody ProductDto productDto, @RequestBody Integer creditId) {
    }

}
