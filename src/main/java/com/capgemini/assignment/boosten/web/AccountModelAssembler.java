package com.capgemini.assignment.boosten.web;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.capgemini.assignment.boosten.model.Account;

@Component
public class AccountModelAssembler implements RepresentationModelAssembler<Account, EntityModel<Account>> {

  @Override
  public EntityModel<Account> toModel(Account account) {
    return EntityModel.of(account, //
        linkTo(methodOn(CustomerController.class).customerAccount(account.getCustomerId(), account.getId())).withSelfRel(),
        linkTo(methodOn(CustomerController.class).customerAccountTransactions(account.getCustomerId(), account.getId())).withRel("customerAccountTransaction"),
        linkTo(methodOn(CustomerController.class).customerAccounts(account.getCustomerId())).withRel("customerAccounts"));
  }
}