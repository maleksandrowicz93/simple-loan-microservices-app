package com.github.maleksandrowicz93.springbootcreditapi;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditRepo extends JpaRepository<Credit, Integer> {
}
