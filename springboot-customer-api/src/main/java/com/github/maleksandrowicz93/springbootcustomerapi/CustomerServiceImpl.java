package com.github.maleksandrowicz93.springbootcustomerapi;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@Log4j
@Component
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepo customerRepo;

    @Override
    public void saveCustomer(Customer customer) throws SQLIntegrityConstraintViolationException {
        if(checkIfCustomerExistsInDB(customer.getPesel()))
            throw new  SQLIntegrityConstraintViolationException();
        customerRepo.save(customer);
        log.info("Adding new customer...");
    }

    private boolean checkIfCustomerExistsInDB(String pesel) {
        List<String> pesels = customerRepo.getPesels();
        return pesels.contains(pesel);
    }

    @Override
    public List<Customer> getCustomers(List<Integer> creditIds) {
        List<Customer> customers = new ArrayList<>();
        creditIds.forEach(id -> customers.addAll(customerRepo.findByCreditId(id)));
        return customers;
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
