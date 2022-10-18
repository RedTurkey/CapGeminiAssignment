package com.capgemini.assignment.boosten.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.capgemini.assignment.boosten.data.IAccountDAO;
import com.capgemini.assignment.boosten.data.ICustomerDAO;
import com.capgemini.assignment.boosten.data.ITransactionDAO;
import com.capgemini.assignment.boosten.main.CapGeminiAssignmentBoostenApplication;
import com.capgemini.assignment.boosten.model.Customer;
import com.capgemini.assignment.boosten.services.AccountServices;
import com.capgemini.assignment.boosten.services.CustomerServices;
import com.capgemini.assignment.boosten.services.TransactionServices;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = CapGeminiAssignmentBoostenApplication.class)
@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {
	@Autowired
	private MockMvc mvc;

	@MockBean
	private CustomerServices customerServices;

	@MockBean
	private AccountServices accountServices;

	@MockBean
	private TransactionServices transactionServices;

	@MockBean
	private ICustomerDAO customerDao;

	@MockBean
	private IAccountDAO accountDao;

	@MockBean
	private ITransactionDAO transactionDao;

	@Test
	public void testGetAllCustomers() throws Exception {
		Customer customer = new Customer("Boosten", "Vincent");

		List<Customer> allEmployees = Arrays.asList(customer);

		given(customerServices.getAllCustomers()).willReturn(allEmployees);

		mvc.perform(get("/customers").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

}
