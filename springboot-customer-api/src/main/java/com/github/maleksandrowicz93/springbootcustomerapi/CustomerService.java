package com.github.maleksandrowicz93.springbootcustomerapi;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerService {

    void saveCustomer(Customer customer);
    List<Customer> getCustomers(List<Integer> creditsIds);
    Customer convertFromDto(CustomerDto customerDto);
    CustomerDto convertFromCustomer(Customer customer);

}
