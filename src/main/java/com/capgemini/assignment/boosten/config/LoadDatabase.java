package com.capgemini.assignment.boosten.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.capgemini.assignment.boosten.data.ICustomerDAO;
import com.capgemini.assignment.boosten.model.Customer;

@Configuration
public class LoadDatabase {

	  private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

	  @Bean
	  CommandLineRunner initDatabase(ICustomerDAO customerDAO) {

	    return args -> {
	    	customerDAO.save(new Customer("Boosten", "Vincent"));
	      customerDAO.save(new Customer("Boosten", "Jonathan"));

	      customerDAO.findAll().forEach(customer -> log.info("Preloaded " + customer));
	    };
	  }
}