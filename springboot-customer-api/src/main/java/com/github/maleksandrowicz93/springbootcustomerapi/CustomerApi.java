package com.github.maleksandrowicz93.springbootcustomerapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerApi {

    @Autowired
    private CustomerService customerService;

    private List<Integer> creditIds = new ArrayList<>();
    private int creditId = 0;

    @GetMapping()
    public List<CustomerDto> GetCustomers() {
        List<Customer> customers = customerService.getCustomers(creditIds);
        return customerService.convertFromCustomerList(customers);
    }

    @PostMapping()
    public void CreateCustomer (@RequestBody CustomerDto customerDto) {
        Customer customer = customerService.convertFromDto(customerDto);
        customer.setCreditId(creditId);
        customerService.saveCustomer(customer);
    }

    @PostMapping("/ids")
    private void setCreditIds(@RequestBody Integer[] ids) {
        creditIds = Arrays.asList(ids);
    }

    @PostMapping("/creditId")
    private  void setCreditId(@RequestBody int id) {
        creditId = id;
    }

}
