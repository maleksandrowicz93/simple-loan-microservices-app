package com.github.maleksandrowicz93.springbootcustomerapi;

import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@Service
public interface CustomerService {

    void saveCustomer(Customer customer) throws SQLIntegrityConstraintViolationException;
    List<Customer> getCustomers(List<Integer> creditsIds);
    Customer convertFromDto(CustomerDto customerDto);
    List<CustomerDto> convertFromCustomerList(List<Customer> customers);
}
