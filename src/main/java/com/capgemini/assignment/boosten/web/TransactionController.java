package com.capgemini.assignment.boosten.web;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.Collection;
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
import com.capgemini.assignment.boosten.model.CustomerDetails;
import com.capgemini.assignment.boosten.model.Transaction;

@RestController
public class TransactionController {
	private final ICustomerDAO customerDao;
	private final ITransactionDAO transactionDao;
	private final IAccountDAO accountDao;

	private final CustomerModelAssembler customerAssembler;
	private final TransactionModelAssembler transactionAssembler;
	private final AccountModelAssembler accountAssembler;

	TransactionController(ICustomerDAO customerDao, CustomerModelAssembler customerAssembler,
			ITransactionDAO transactionDao, TransactionModelAssembler transactionAssembler, IAccountDAO accountDao,
			AccountModelAssembler accountAssembler) {

		this.customerDao = customerDao;
		this.customerAssembler = customerAssembler;
		this.transactionDao = transactionDao;
		this.transactionAssembler = transactionAssembler;
		this.accountDao = accountDao;
		this.accountAssembler = accountAssembler;
	}

	@GetMapping("/transactions")
	CollectionModel<EntityModel<Transaction>> all() {

		List<EntityModel<Transaction>> transactions = transactionDao.findAll().stream() //
				.map(transactionAssembler::toModel) //
				.collect(Collectors.toList());

		return CollectionModel.of(transactions, linkTo(methodOn(TransactionController.class).all()).withSelfRel());
	}
	
	@GetMapping("/transactions/{transactionId}")
	EntityModel<Transaction> one(@PathVariable Long transactionId) {
		Transaction transaction = transactionDao.findById(transactionId) //
				.orElseThrow(() -> new TransactionNotFoundException(transactionId));

		return transactionAssembler.toModel(transaction);
	}
}