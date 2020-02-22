package com.github.maleksandrowicz93.springbootcustomerapi;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Log4j
@Component
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepo customerRepo;

    @Override
    public void saveCustomer(Customer customer) {
        customerRepo.save(customer);
        log.info("Customer already has been saved.");
    }

    @Override
    public List<Customer> getCustomers(List<Integer> creditsIds) {
        return null;
    }

    @Override
    public Customer convertFromDto(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setFirstName(customerDto.getFirstName());
        customer.setSurname(customerDto.getSurname());
        customer.setPesel(customerDto.getPesel());
        return customer;
    }

    @Override
    public CustomerDto convertFromCustomer(Customer customer) {
        return null;
    }

}
