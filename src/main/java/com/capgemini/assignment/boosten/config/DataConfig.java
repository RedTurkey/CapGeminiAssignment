package com.capgemini.assignment.boosten.config;

import javax.sql.DataSource;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import com.capgemini.assignment.boosten.data.IAccountDAO;
import com.capgemini.assignment.boosten.data.ICustomerDAO;
import com.capgemini.assignment.boosten.data.ITransactionDAO;
import com.capgemini.assignment.boosten.model.Account;
import com.capgemini.assignment.boosten.model.Customer;
import com.capgemini.assignment.boosten.model.Transaction;

@Configuration
public class DataConfig {
	private static final Logger log = LoggerFactory.getLogger(DataConfig.class);

	@Bean
	@Profile("dev")
	CommandLineRunner initDatabase(ICustomerDAO customerDAO, IAccountDAO accountDAO, ITransactionDAO transactionDao) {

		return args -> {
			Customer c5 = new Customer("System", "Bank");
			Customer c1 = new Customer("Boosten", "Vincent");
			Customer c2 = new Customer("Boosten", "Jonathan");
			Customer c3 = new Customer("Boosten", "Jeremy");
			Customer c4 = new Customer("Taburiaux", "Alyson");

			c5 = customerDAO.save(c5);
			c1 = customerDAO.save(c1);
			c2 = customerDAO.save(c2);
			c3 = customerDAO.save(c3);
			c4 = customerDAO.save(c4);

			Account a1 = new Account(c1);
			Account a2 = new Account(c1);
			Account a3 = new Account(c1);
			Account a4 = new Account(c2);
			Account a5 = new Account(c2);
			Account a6 = new Account(c3);
			Account a7 = new Account(c5);
			
			a7.setBalance(1000000);
			a7 = accountDAO.save(a7);

			a1 = accountDAO.save(a1);
			a2 = accountDAO.save(a2);
			a3 = accountDAO.save(a3);
			a4 = accountDAO.save(a4);
			a5 = accountDAO.save(a5);
			a6 = accountDAO.save(a6);

			Transaction t1 = new Transaction(10, "You owed me 10", a1, a2, a1);
			Transaction t2 = new Transaction(5, "You owed me 5", a1, a1, a2);
			Transaction t3 = new Transaction(15.5, "You owed me 15.5", a2, a3, a2);
			Transaction t4 = new Transaction(20, "You owed me 20", a2, a4, a2);
			Transaction t5 = new Transaction(35, "You owed me 35", a5, a5, a1);
			Transaction t6 = new Transaction(2.5, "You owed me 2.5", a3, a5, a3);
			Transaction t7 = new Transaction(3.3, "You owed me 3.3", a2, a3, a2);
			Transaction t8 = new Transaction(7, "You owed me 7", a4, a4, a1);
			Transaction t9 = new Transaction(4, "You owed me 4", a3, a3, a5);
			Transaction t10 = new Transaction(40, "You owed me 40", a5, a3, a5);
			Transaction t11 = new Transaction(24, "You owed me 24", a1, a2, a1);
			Transaction t12 = new Transaction(100, "You owed me 100", a2, a1, a2);

			transactionDao.save(t1);
			transactionDao.save(t2);
			transactionDao.save(t3);
			transactionDao.save(t4);
			transactionDao.save(t5);
			transactionDao.save(t6);
			transactionDao.save(t7);
			transactionDao.save(t8);
			transactionDao.save(t9);
			transactionDao.save(t10);
			transactionDao.save(t11);
			transactionDao.save(t12);

			a1.setBalance(-29);
			a2.setBalance(109.8);
			a3.setBalance(-60.3);
			a4.setBalance(-27);
			a5.setBalance(6.5);
			a6.setBalance(0);

			a1 = accountDAO.save(a1);
			a2 = accountDAO.save(a2);
			a3 = accountDAO.save(a3);
			a4 = accountDAO.save(a4);
			a5 = accountDAO.save(a5);
			a6 = accountDAO.save(a6);

			customerDAO.findAllWithAccounts().forEach(customer -> log.info("Preloaded " + customer));
			accountDAO.findAll().forEach(account -> log.info("Preloaded " + account));
			transactionDao.findAll().forEach(transaction -> log.info("Preloaded " + transaction));
		};
	}

	@Bean
	@Profile("test")
	public DataSource dataSourceTest() {
		return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).build();
	}

	@Bean
	@Profile("dev")
	public DataSource dataSourceServeur() {
		return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).build();
	}

	@Bean
	@Profile("pro")
	public DataSource dataSourceTCPServeur() {
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName("org.h2.Driver");
		ds.setUrl("jdbc:h2:tcp://localhost//"); // To complete
		ds.setUsername("sa");
		ds.setPassword("");
		ds.setInitialSize(5);
		ds.setMaxTotal(10);
		return ds;
	}

}