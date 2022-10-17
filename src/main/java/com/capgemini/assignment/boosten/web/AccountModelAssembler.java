package com.capgemini.assignment.boosten.web;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.capgemini.assignment.boosten.model.Account;
import com.capgemini.assignment.boosten.model.AccountStatus;

@Component
public class AccountModelAssembler implements RepresentationModelAssembler<Account, EntityModel<Account>> {

	@Override
	public EntityModel<Account> toModel(Account account) {
		EntityModel<Account> entityModel = EntityModel.of(account,
				linkTo(methodOn(AccountController.class).one(account.getId())).withSelfRel(),
				linkTo(methodOn(AccountController.class).accountTransactions(account.getId()))
						.withRel("account's transactions"),
				linkTo(methodOn(CustomerController.class).one(account.getCustomerId())).withRel("account's customer"),
				linkTo(methodOn(AccountController.class).all()).withRel("all"));

		if (account.getStatus() == AccountStatus.OPEN) {
			entityModel.add(linkTo(methodOn(AccountController.class).closeAccount(account.getId(), null)).withRel("close account"));
		}

		return entityModel;
	}
}