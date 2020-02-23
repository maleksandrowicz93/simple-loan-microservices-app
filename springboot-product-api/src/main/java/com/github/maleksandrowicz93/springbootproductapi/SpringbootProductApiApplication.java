package com.github.maleksandrowicz93.springbootproductapi;

import org.apache.log4j.BasicConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootProductApiApplication {

	public static void main(String[] args) {

		BasicConfigurator.configure();

		SpringApplication.run(SpringbootProductApiApplication.class, args);

	}

}
