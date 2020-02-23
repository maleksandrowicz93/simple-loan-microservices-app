package com.github.maleksandrowicz93.springbootcustomerapi;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Log4j
@Component
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepo customerRepo;

    @Autowired
    public CustomerServiceImpl(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    @Override
    public void saveCustomer(Customer customer) {
        try {
            checkIfCustomerExistsInDB(customer.getPesel());
            log.info("Adding new customer...");
            customerRepo.save(customer);
        } catch (SQLIntegrityConstraintViolationException e) {
            log.error("Customer with the same pesel exists in DB!");
            e.printStackTrace();
        }
    }

    private void checkIfCustomerExistsInDB(String pesel) throws SQLIntegrityConstraintViolationException {
        log.info("Checking if customer exists in DB by pesel");
        List<String> pesels = customerRepo.getPesels();
        if(pesels.contains(pesel))
            throw new  SQLIntegrityConstraintViolationException();
    }

    @Override
    public List<Customer> getCustomers(List<Integer> creditIds) {
        log.info("Getting customers...");
        List<Customer> allCustomers = customerRepo.findAll();
        return allCustomers.stream()
                .filter(c -> creditIds.contains(c.getCreditId()))
                .collect(Collectors.toList());
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
    public List<CustomerDto> convertFromCustomerList(List<Customer> customers) {
        log.info("Converting Customer list to CustomerDto one...");
        List<CustomerDto> customerDtoList = new ArrayList<>();
        customers.forEach(c -> customerDtoList.add(convertFromCustomer(c)));
        return customerDtoList;
    }

    private CustomerDto convertFromCustomer(Customer customer) {
        log.info("Converting Customer instance to CustomerDto one...");
        CustomerDto customerDto = new CustomerDto();
        customerDto.setFirstName(customer.getFirstName());
        customerDto.setSurname(customer.getSurname());
        customerDto.setPesel(customer.getPesel());
        return customerDto;
    }

}
