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
        log.info("Getting customers...");
        List<Customer> customers = customerService.getCustomers(creditIds);
        List<CustomerDto> customerDtoList = new ArrayList<>();
        log.info("Converting Customer list to CustomerDto one...");
        customers.forEach(c -> customerDtoList.add(customerService.convertFromCustomer(c)));
        return customerDtoList;
    }

    @PostMapping("/customer")
    public void saveCustomer(@RequestBody CustomerDto customerDto) throws SQLIntegrityConstraintViolationException {
        log.info("Converting CustomerDto instance to Customer one...");
        Customer customer = customerService.convertFromDto(customerDto);
        log.info("Adding new customer...");
        try {
            customerService.saveCustomer(customer);
        } catch (SQLIntegrityConstraintViolationException e) {
            log.error("Customer with the same pesel exists!");
            e.printStackTrace();
        }
    }

}
