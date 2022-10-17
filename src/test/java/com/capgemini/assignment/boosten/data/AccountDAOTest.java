package com.capgemini.assignment.boosten.data;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.capgemini.assignment.boosten.main.CapGeminiAssignmentBoostenApplication;
import com.capgemini.assignment.boosten.model.Account;
import com.capgemini.assignment.boosten.model.Customer;

@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes = CapGeminiAssignmentBoostenApplication.class)
public class AccountDAOTest {
	@Autowired
	private ICustomerDAO customerDao;

	@Autowired
	private IAccountDAO accountDao;

	private Account toUpdateAccount;
	private Account toDeleteAccount;
	private Customer basicCustomer;

	@Before
	public void setup() {
		Customer c3 = new Customer("System", "Bank");
		Customer c1 = new Customer("Boosten", "Vincent");
		Customer c2 = new Customer("Boosten", "Jonathan");

		c3 = customerDao.save(c3);
		c1 = customerDao.save(c1);
		c2 = customerDao.save(c2);

		Account a1 = new Account(c1);
		Account a2 = new Account(c2);
		Account a3 = new Account(c3);

		a1 = accountDao.save(a1);
		a2 = accountDao.save(a2);
		a3 = accountDao.save(a3);

		this.toUpdateAccount = a1;
		this.toDeleteAccount = a2;
		this.basicCustomer = c3;
	}

	@Test
	public void testCreation() {
		Account a1 = new Account(basicCustomer);

		assertThat(a1.getId()).isNull();

		a1 = accountDao.save(a1);

		assertThat(a1.getId()).isNotNull();

		Account a1a = accountDao.findById(a1.getId()).get();

		assertThat(a1a).isEqualTo(a1);
	}

	@Test
	public void testUpdate() {
		assertThat(toUpdateAccount.getBalance()).isEqualTo(0);

		toUpdateAccount.setBalance(10);

		toUpdateAccount = accountDao.save(toUpdateAccount);

		assertThat(toUpdateAccount.getBalance()).isEqualTo(10);
	}

	@Test
	public void testDeletion() {
		assertThat(toDeleteAccount).isNotNull();

		accountDao.delete(toDeleteAccount);

		assertThrows(NoSuchElementException.class, () -> {
			toDeleteAccount = accountDao.findById(toDeleteAccount.getId()).get();
		});
	}
}
