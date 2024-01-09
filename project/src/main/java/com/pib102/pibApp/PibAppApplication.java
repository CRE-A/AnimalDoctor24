package com.pib102.pibApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


@SpringBootApplication
public class PibAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(PibAppApplication.class, args);
	}

}
