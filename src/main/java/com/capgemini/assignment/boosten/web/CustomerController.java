package com.capgemini.assignment.boosten.web;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.assignment.boosten.config.LoadDatabase;
import com.capgemini.assignment.boosten.data.IAccountDAO;
import com.capgemini.assignment.boosten.data.ICustomerDAO;
import com.capgemini.assignment.boosten.data.ITransactionDAO;
import com.capgemini.assignment.boosten.exception.AccountNotFoundException;
import com.capgemini.assignment.boosten.exception.CustomerNotFoundException;
import com.capgemini.assignment.boosten.exception.TransactionNotFoundException;
import com.capgemini.assignment.boosten.model.Account;
import com.capgemini.assignment.boosten.model.Customer;
import com.capgemini.assignment.boosten.model.CustomerDetails;
import com.capgemini.assignment.boosten.model.Transaction;

/**
 * The main and only controller since most data relate around the customer,
 * albeit there was supposed to be an account controller and a transaction
 * controller, purely to have their individual data easily accessible without
 * having to send the customer id every time
 */
@RestController
public class CustomerController {
	private final ICustomerDAO customerDao;
	private final ITransactionDAO transactionDao;
	private final IAccountDAO accountDao;

	private final CustomerModelAssembler customerAssembler;
	private final TransactionModelAssembler transactionAssembler;
	private final AccountModelAssembler accountAssembler;

	private static final Logger log = LoggerFactory.getLogger(CustomerController.class);

	CustomerController(ICustomerDAO customerDao, CustomerModelAssembler customerAssembler,
			ITransactionDAO transactionDao, TransactionModelAssembler transactionAssembler, IAccountDAO accountDao,
			AccountModelAssembler accountAssembler) {

		this.customerDao = customerDao;
		this.customerAssembler = customerAssembler;
		this.transactionDao = transactionDao;
		this.transactionAssembler = transactionAssembler;
		this.accountDao = accountDao;
		this.accountAssembler = accountAssembler;
	}

	@GetMapping("/customers")
	CollectionModel<EntityModel<Customer>> all() {

		List<EntityModel<Customer>> customers = customerDao.findAll().stream() //
				.map(customerAssembler::toModel) //
				.collect(Collectors.toList());

		return CollectionModel.of(customers, linkTo(methodOn(CustomerController.class).all()).withSelfRel());
	}

	@PostMapping("/customers")
	ResponseEntity<?> newCustomer(@RequestBody Customer newCustomer) {

		EntityModel<Customer> entityModel = customerAssembler.toModel(customerDao.save(newCustomer));

		return ResponseEntity //
				.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
				.body(entityModel);
	}

	@GetMapping("/customers/{customerId}")
	EntityModel<Customer> one(@PathVariable Long customerId) {
		Customer customer = customerDao.findById(customerId) //
				.orElseThrow(() -> new CustomerNotFoundException(customerId));

		return customerAssembler.toModel(customer);
	}

	@GetMapping("/customers/{customerId}/details")
	CustomerDetails customerDetails(@PathVariable Long customerId) {
		Customer customer = customerDao.findByIdWithAccounts(customerId) //
				.orElseThrow(() -> new CustomerNotFoundException(customerId));

		Collection<Transaction> transactions = new ArrayList<>();

		Collection<Account> accounts = customer.getAccounts();

		for (Account account : accounts) {
			Collection<Transaction> accountTransactions = account.getTransactions();

			for (Transaction transaction : accountTransactions) {
				transactions.add(transaction);
			}
		}

		CustomerDetails details = new CustomerDetails(customer.getName(), customer.getSurname(), customer.getBalance(),
				transactions);

		return details;
	}

	@PutMapping("/customers/{customerId}")
	ResponseEntity<?> replaceCustomer(@RequestBody Customer newCustomer, @PathVariable Long customerId) {
		Customer updatedCustomer = customerDao.findById(customerId) //
				.map(customer -> {
					customer.setName(newCustomer.getName());
					customer.setSurname(newCustomer.getSurname());
					return customerDao.save(customer);
				}) //
				.orElseThrow(() -> new CustomerNotFoundException(customerId));

		EntityModel<Customer> entityModel = customerAssembler.toModel(updatedCustomer);

		return ResponseEntity //
				.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
				.body(entityModel);
	}

	@GetMapping("/customers/{customerId}/accounts")
	CollectionModel<EntityModel<Account>> customerAccounts(@PathVariable Long customerId) {
		Customer customer = customerDao.findByIdWithAccounts(customerId) //
				.orElseThrow(() -> new CustomerNotFoundException(customerId));

		List<EntityModel<Account>> accounts = customer.getAccounts().stream() //
				.map(accountAssembler::toModel) //
				.collect(Collectors.toList());

		return CollectionModel.of(accounts,
				linkTo(methodOn(CustomerController.class).customerAccounts(customerId)).withSelfRel());
	}

	@GetMapping("/customers/{customerId}/transactions")
	CollectionModel<EntityModel<Transaction>> customerTransactions(@PathVariable Long customerId) {
		Customer customer = customerDao.findByIdWithAccounts(customerId) //
				.orElseThrow(() -> new CustomerNotFoundException(customerId));

		Collection<Transaction> transactions = new ArrayList<>();

		Collection<Account> accounts = customer.getAccounts();

		for (Account account : accounts) {
			Collection<Transaction> accountTransactions = account.getTransactions();

			for (Transaction transaction : accountTransactions) {
				transactions.add(transaction);
			}
		}

		List<EntityModel<Transaction>> transactionModels = transactions.stream() //
				.map(transactionAssembler::toModel) //
				.collect(Collectors.toList());

		return CollectionModel.of(transactionModels,
				linkTo(methodOn(CustomerController.class).customerTransactions(customerId)).withSelfRel());
	}

	@PostMapping("/customers/{customerId}/accounts/{initialCredit}")
	ResponseEntity<?> newCustomerAccount(@PathVariable Long customerId, @PathVariable double initialCredit) {
		Customer customer = customerDao.findByIdWithAccounts(customerId) //
				.orElseThrow(() -> new CustomerNotFoundException(customerId));

		Account newAccount = new Account(customer);

		newAccount = accountDao.save(newAccount);

		if (initialCredit > 0) {
			Transaction transaction = new Transaction(initialCredit, "Cash in on creation", //
					newAccount, newAccount, newAccount);
			transaction = transactionDao.save(transaction);

			newAccount.setBalance(initialCredit);

			newAccount = accountDao.save(newAccount);
		}

		customer.addAccount(newAccount);

		customerDao.save(customer);

		newAccount = accountDao.save(newAccount);

		EntityModel<Account> entityAccount = accountAssembler.toModel(newAccount);

		return ResponseEntity //
				.created(entityAccount.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
				.body(entityAccount);
	}
}
