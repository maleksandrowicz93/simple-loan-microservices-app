package com.github.maleksandrowicz93.springbootcustomerapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerApi {

    @Autowired
    private CustomerService customerService;

    @GetMapping()
    public List<CustomerDto> GetCustomers(@RequestBody List<Integer> creditIds) {
        List<Customer> customers = customerService.getCustomers(creditIds);
        return customerService.convertFromCustomerList(customers);
    }

    @PostMapping()
    public void CreateCustomer (@RequestBody CustomerDto customerDto) {
        Customer customer = customerService.convertFromDto(customerDto);
        customerService.saveCustomer(customer);
    }

}
