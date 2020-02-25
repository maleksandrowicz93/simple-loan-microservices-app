package com.github.maleksandrowicz93.springbootcustomerapi;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Log4j
@Component
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepo customerRepo;

    @Override
    public List<Customer> getCustomers(List<Integer> creditIds) {
        log.info("Getting customers...");
        List<Customer> allCustomers = customerRepo.findAll();
        return allCustomers.stream()
                .filter(c -> creditIds.contains(c.getCreditId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<CustomerDto> convertFromCustomerList(List<Customer> customers) {
        log.info("Converting Customer list to CustomerDto one...");
        return customers.stream()
                .sorted(Comparator.comparing(Customer::getCreditId))
                .map(this::convertFromCustomer)
                .collect(Collectors.toList());
    }

    private CustomerDto convertFromCustomer(Customer customer) {
        log.info("Converting Customer instance to CustomerDto one...");
        CustomerDto customerDto = new CustomerDto();
        customerDto.setFirstName(customer.getFirstName());
        customerDto.setSurname(customer.getSurname());
        customerDto.setPesel(customer.getPesel());
        return customerDto;
    }

    @Override
    public Customer convertFromDto(CustomerDto customerDto) {
        log.info("Converting CustomerDto instance to Customer one...");
        Customer customer = new Customer();
        customer.setFirstName(customerDto.getFirstName());
        customer.setSurname(customerDto.getSurname());
        customer.setPesel(customerDto.getPesel());
        return customer;
    }

    @Override
    public void saveCustomer(Customer customer) {
        log.info("Adding new customer...");
        customerRepo.save(customer);
    }

}
