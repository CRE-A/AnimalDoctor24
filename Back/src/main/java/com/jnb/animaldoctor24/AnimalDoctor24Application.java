package com.jnb.animaldoctor24;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class AnimalDoctor24Application {

	public static void main(String[] args) {
		SpringApplication.run(AnimalDoctor24Application.class, args);
	}
//test
}
