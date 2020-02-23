package com.github.maleksandrowicz93.springbootcustomerapi;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@Log4j
@RestController
public class CustomerApi {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/customer")
    public List<CustomerDto> getCustomers(@RequestBody List<Integer> creditIds) {
        List<Customer> customers = customerService.getCustomers(creditIds);
        return customerService.convertFromCustomerList(customers);
    }

    @PostMapping("/customer")
    public void saveCustomer(@RequestBody CustomerDto customerDto) throws SQLIntegrityConstraintViolationException {
        Customer customer = customerService.convertFromDto(customerDto);
        customerService.saveCustomer(customer);
    }

}
