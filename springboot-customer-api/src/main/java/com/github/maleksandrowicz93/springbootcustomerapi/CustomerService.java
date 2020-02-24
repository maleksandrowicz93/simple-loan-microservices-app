package com.github.maleksandrowicz93.springbootcustomerapi;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerService {

    List<Customer> getCustomers(List<Integer> creditsIds);
    List<CustomerDto> convertFromCustomerList(List<Customer> customers);
    Customer convertFromDto(CustomerDto customerDto);
    void saveCustomer(Customer customer);

}
