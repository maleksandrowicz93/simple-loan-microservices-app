package com.github.maleksandrowicz93.springbootproductapi;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.beans.Customizer;

@Repository
public interface ProductRepo extends JpaRepository<Customizer, Integer> {
}
