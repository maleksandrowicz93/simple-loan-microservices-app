package com.github.maleksandrowicz93.springbootcustomerapi;

import java.util.List;

public interface CustomerService {

    void saveCustomer(Customer customer);
    List<Customer> getCustomers(List<Integer> creditsIds);
    Customer convertFromDto(CustomerDto customerDto);
    CustomerDto convertFromCustomer(Customer customer);

}
