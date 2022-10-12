package com.capgemini.assignment.boosten.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.capgemini.assignment.boosten.data.IAccountDAO;
import com.capgemini.assignment.boosten.data.ICustomerDAO;
import com.capgemini.assignment.boosten.model.Customer;

@Configuration
public class LoadDatabase {

	private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

	@Bean
	CommandLineRunner initDatabase(ICustomerDAO customerDAO, IAccountDAO accountDAO) {

		return args -> {
			Customer c1 = new Customer("Boosten", "Vincent");
			c1.createAccount();
			
			customerDAO.save(c1);
			customerDAO.save(new Customer("Boosten", "Jonathan"));

			customerDAO.findAllWithAccounts().forEach(customer -> log.info("Preloaded " + customer));
		};
	}
}