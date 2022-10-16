package com.capgemini.assignment.boosten.web;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.assignment.boosten.data.ITransactionDAO;
import com.capgemini.assignment.boosten.exception.TransactionNotFoundException;
import com.capgemini.assignment.boosten.model.Transaction;

@RestController
public class TransactionController {
	private final ITransactionDAO transactionDao;

	private final TransactionModelAssembler transactionAssembler;

	TransactionController(ITransactionDAO transactionDao, TransactionModelAssembler transactionAssembler) {
		this.transactionDao = transactionDao;
		this.transactionAssembler = transactionAssembler;
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