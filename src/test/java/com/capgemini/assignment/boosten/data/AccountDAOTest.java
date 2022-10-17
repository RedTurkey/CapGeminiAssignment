package com.capgemini.assignment.boosten.data;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
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
    private TestEntityManager entityManager;

    @Autowired
    private IAccountDAO accountDao;
    
    @Test
    public void checkCustomerCreation() {
		Customer c1 = new Customer("System", "Bank");
		Account a1 = new Account(c1);
		
		assertThat(a1.getId()).isNull();
    }
}
