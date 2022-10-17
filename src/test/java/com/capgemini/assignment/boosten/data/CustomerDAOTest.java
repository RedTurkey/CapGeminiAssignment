package com.capgemini.assignment.boosten.data;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.capgemini.assignment.boosten.main.CapGeminiAssignmentBoostenApplication;
import com.capgemini.assignment.boosten.model.Customer;

@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes = CapGeminiAssignmentBoostenApplication.class)
public class CustomerDAOTest {
    @Autowired
    private ICustomerDAO customerDao;

    private Customer toUpdateCustomer;
    private Customer toDeleteCustomer;
    
    @Before
    public void setup() {
		Customer c5 = new Customer("System", "Bank");
		Customer c1 = new Customer("Boosten", "Vincent");
		Customer c2 = new Customer("Boosten", "Jonathan");

		c5 = customerDao.save(c5);
		c1 = customerDao.save(c1);
		c2 = customerDao.save(c2);

		this.toUpdateCustomer = c1;
		this.toDeleteCustomer = c2;
    }
    
    @Test
    public void testCreation() {
		Customer c3 = new Customer("Boosten", "Jeremy");
		
		assertThat(c3.getId()).isNull();

		c3 = customerDao.save(c3);

		assertThat(c3.getId()).isNotNull();
		
		Customer c3c = customerDao.findById(c3.getId()).get();
		
		assertThat(c3c).isEqualTo(c3);
    }
    
    @Test
    public void testUpdate() {
    	assertThat(toUpdateCustomer.getSurname()).isEqualTo("Vincent");
    	
    	toUpdateCustomer.setSurname("Michel");
    	
    	toUpdateCustomer = customerDao.save(toUpdateCustomer);
    	
    	assertThat(toUpdateCustomer.getSurname()).isEqualTo("Michel");
    }
    
    @Test
    public void testDeletion() {
    	assertThat(toDeleteCustomer).isNotNull();
    	
    	customerDao.delete(toDeleteCustomer);
    	
    	assertThrows(NoSuchElementException.class, () -> {
        	toDeleteCustomer = customerDao.findById(toDeleteCustomer.getId()).get();
    	});
    }
}
