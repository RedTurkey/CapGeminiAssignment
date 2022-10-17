package com.capgemini.assignment.boosten.web;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.capgemini.assignment.boosten.model.Transaction;

@Component
public class TransactionModelAssembler implements RepresentationModelAssembler<Transaction, EntityModel<Transaction>> {

	@Override
	public EntityModel<Transaction> toModel(Transaction transaction) {
		return EntityModel.of(transaction,
				linkTo(methodOn(TransactionController.class).one(transaction.getId())).withSelfRel(),
				linkTo(methodOn(AccountController.class).one(transaction.getCreatorId()))
						.withRel("transaction's creator account"),
				linkTo(methodOn(AccountController.class).one(transaction.getReceiverId()))
						.withRel("transaction's receiver account"),
				linkTo(methodOn(AccountController.class).one(transaction.getSenderId()))
						.withRel("transaction's sender account"),
				linkTo(methodOn(CustomerController.class).one(transaction.getCreatorCustomerId()))
						.withRel("transaction's creator customer"),
				linkTo(methodOn(CustomerController.class).one(transaction.getReceiverCustomerId()))
						.withRel("transaction's receiver customer"),
				linkTo(methodOn(CustomerController.class).one(transaction.getSenderCustomerId()))
						.withRel("transaction's sender customer"),
				linkTo(methodOn(TransactionController.class).all()).withRel("all"));
	}
}