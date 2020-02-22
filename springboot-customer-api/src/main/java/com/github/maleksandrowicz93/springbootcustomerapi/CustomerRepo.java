package com.github.maleksandrowicz93.springbootcustomerapi;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Integer> {

    @Query(value = "SELECT pesel FROM customer", nativeQuery = true)
    List<String> getPesels();

    List<Customer> findByCreditId(int creditId);

}
