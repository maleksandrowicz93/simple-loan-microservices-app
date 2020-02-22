package com.github.maleksandrowicz93.springbootcustomerapi;

import org.apache.log4j.BasicConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootCustomerApiApplication {

	public static void main(String[] args) {

		BasicConfigurator.configure();

		SpringApplication.run(SpringbootCustomerApiApplication.class, args);

	}

}
