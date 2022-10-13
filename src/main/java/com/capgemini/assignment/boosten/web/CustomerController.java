package com.capgemini.assignment.boosten.web;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

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

import com.capgemini.assignment.boosten.data.IAccountDAO;
import com.capgemini.assignment.boosten.data.ICustomerDAO;
import com.capgemini.assignment.boosten.data.ITransactionDAO;
import com.capgemini.assignment.boosten.exception.AccountNotFoundException;
import com.capgemini.assignment.boosten.exception.CustomerNotFoundException;
import com.capgemini.assignment.boosten.exception.TransactionNotFoundException;
import com.capgemini.assignment.boosten.model.Account;
import com.capgemini.assignment.boosten.model.Customer;
import com.capgemini.assignment.boosten.model.Transaction;

@RestController
public class CustomerController {
	private final ICustomerDAO customerDao;
	private final ITransactionDAO transactionDao;
	private final IAccountDAO accountDao;

	private final CustomerModelAssembler customerAssembler;
	private final TransactionModelAssembler transactionAssembler;
	private final AccountModelAssembler accountAssembler;

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
				.map(accountId -> {
					Account account = accountDao.findById(accountId)
							.orElseThrow(() -> new AccountNotFoundException(accountId));
					return accountAssembler.toModel(account);
				}) //
				.collect(Collectors.toList());

		return CollectionModel.of(accounts, linkTo(methodOn(CustomerController.class).customerAccounts(customerId)).withSelfRel());
	}

	@GetMapping("/customers/{customerId}/accounts/{accountId}")
	EntityModel<Account> customerAccount(@PathVariable Long customerId,
			@PathVariable Long accountId) {
		Account account = accountDao.findById(accountId) //
				.orElseThrow(() -> new AccountNotFoundException(accountId));

		return accountAssembler.toModel(account);
	}

	@GetMapping("/customers/{customerId}/accounts/{accountId}/transactions")
	CollectionModel<EntityModel<Transaction>> customerAccountTransactions(@PathVariable Long customerId,
			@PathVariable Long accountId) {
		Account account = accountDao.findById(accountId) //
				.orElseThrow(() -> new AccountNotFoundException(accountId));

		List<EntityModel<Transaction>> transactions = account.getTransactions().stream() //
				.map(transaction -> {
					return transactionAssembler.toModel(transaction);
				}) //
				.collect(Collectors.toList());

		return CollectionModel.of(transactions, linkTo(methodOn(CustomerController.class).customerAccountTransactions(customerId, accountId)).withSelfRel());
	}

	@GetMapping("/customers/{customerId}/accounts/{accountId}/transactions/{transactionId}")
	EntityModel<Transaction> customerAccountTransaction(@PathVariable Long customerId,
			@PathVariable Long accountId, @PathVariable Long transactionId) {
		Transaction transaction = transactionDao.findById(transactionId) //
				.orElseThrow(() -> new TransactionNotFoundException(transactionId));

		return transactionAssembler.toModel(transaction);
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

			newAccount.addCreatedTransaction(transaction);
			newAccount.addReceivedTransaction(transaction);
			newAccount.addSendTransaction(transaction);

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
