package com.github.maleksandrowicz93.springbootcreditapi;

import org.apache.log4j.BasicConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootCreditApiApplication {

	public static void main(String[] args) {

		BasicConfigurator.configure();

		SpringApplication.run(SpringbootCreditApiApplication.class, args);

	}

}
