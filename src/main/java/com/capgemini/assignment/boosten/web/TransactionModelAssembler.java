package com.capgemini.assignment.boosten.web;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.capgemini.assignment.boosten.model.Transaction;

@Component
public class TransactionModelAssembler implements RepresentationModelAssembler<Transaction, EntityModel<Transaction>> {

  @Override
  public EntityModel<Transaction> toModel(Transaction transaction) {
    return EntityModel.of(transaction);
  }
}