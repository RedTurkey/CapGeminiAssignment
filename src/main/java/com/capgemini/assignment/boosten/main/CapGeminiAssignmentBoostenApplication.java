package com.capgemini.assignment.boosten.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("com.capgemini.assignment.boosten")
@EntityScan("com.capgemini.assignment.boosten.model")
@EnableJpaRepositories("com.capgemini.assignment.boosten.data")
public class CapGeminiAssignmentBoostenApplication {

	public static void main(String[] args) {
		SpringApplication.run(CapGeminiAssignmentBoostenApplication.class, args);
	}

}
