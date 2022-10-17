package com.capgemini.assignment.boosten.data;

import static org.assertj.core.api.Assertions.assertThat;

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
import com.capgemini.assignment.boosten.model.Transaction;

@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes = CapGeminiAssignmentBoostenApplication.class)
public class TransactionDAOTest {
	@Autowired
	private ITransactionDAO transactionDao;

	@Autowired
	private ICustomerDAO customerDao;

	@Autowired
	private IAccountDAO accountDao;

	private Account basicAccount1;
	private Account basicAccount2;

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

		Transaction t1 = new Transaction(10, "You owed me 10", a1, a2, a1);
		Transaction t2 = new Transaction(5, "You owed me 5", a1, a1, a2);
		Transaction t3 = new Transaction(15.5, "You owed me 15.5", a2, a3, a2);

		t1 = transactionDao.save(t1);
		t2 = transactionDao.save(t2);
		t3 = transactionDao.save(t3);

		this.basicAccount1 = a1;
		this.basicAccount2 = a2;
	}

	@Test
	public void testCreation() {
		Transaction t1 = new Transaction(10, "You owed me 10", basicAccount1, basicAccount1, basicAccount2);

		assertThat(t1.getId()).isNull();

		t1 = transactionDao.save(t1);

		assertThat(t1.getId()).isNotNull();

		Transaction t1t = transactionDao.findById(t1.getId()).get();

		assertThat(t1t).isEqualTo(t1);
	}
}
