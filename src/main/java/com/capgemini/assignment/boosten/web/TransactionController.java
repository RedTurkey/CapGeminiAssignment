package com.capgemini.assignment.boosten.web;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.assignment.boosten.dto.TransactionPostDTO;
import com.capgemini.assignment.boosten.model.Transaction;

import lombok.AllArgsConstructor;
import services.TransactionServices;

@RestController
@AllArgsConstructor
public class TransactionController {
	private final TransactionServices transactionServices;

	private final TransactionModelAssembler transactionAssembler;

	@GetMapping("/transactions")
	CollectionModel<EntityModel<Transaction>> all() {
		List<EntityModel<Transaction>> transactions = transactionServices.getAllTransactions().stream() //
				.map(transactionAssembler::toModel) //
				.collect(Collectors.toList());

		return CollectionModel.of(transactions, linkTo(methodOn(TransactionController.class).all()).withSelfRel());
	}

	@PostMapping("/transactions")
	EntityModel<Transaction> newTransaction(@RequestBody TransactionPostDTO newTransactionDTO) {
		return transactionAssembler.toModel(transactionServices.createTransaction(newTransactionDTO.getAmount(),
				newTransactionDTO.getCommunication(), newTransactionDTO.getCreatorId(), newTransactionDTO.getSenderId(),
				newTransactionDTO.getReceiverId()));
	}

	@GetMapping("/transactions/{transactionId}")
	EntityModel<Transaction> one(@PathVariable Long transactionId) {
		return transactionAssembler.toModel(transactionServices.getOneTransaction(transactionId));
	}
}