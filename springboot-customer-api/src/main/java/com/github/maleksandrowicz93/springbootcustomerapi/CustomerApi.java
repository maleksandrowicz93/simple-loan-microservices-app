package com.github.maleksandrowicz93.springbootcustomerapi;

import lombok.extern.log4j.Log4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j
@RestController
public class CustomerApi {

    @GetMapping("/customer")
    public List<Customer> getCustomers(@RequestBody List<Integer> creditsIds) {
        log.info("Getting customers...");
        return null;
    }

    @PostMapping("/customer")
    public void saveCustomer(@RequestBody CustomerDto customerDto) {
        log.info("Adding new customer...");
    }

}