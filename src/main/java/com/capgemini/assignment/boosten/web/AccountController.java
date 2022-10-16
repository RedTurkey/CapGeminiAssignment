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

import com.capgemini.assignment.boosten.data.IAccountDAO;
import com.capgemini.assignment.boosten.exception.AccountNotFoundException;
import com.capgemini.assignment.boosten.model.Account;

@RestController
public class AccountController {
	private final IAccountDAO accountDao;

	private final AccountModelAssembler accountAssembler;

	AccountController(IAccountDAO accountDao, AccountModelAssembler accountAssembler) {
		this.accountDao = accountDao;
		this.accountAssembler = accountAssembler;
	}

	@GetMapping("/accounts")
	CollectionModel<EntityModel<Account>> all() {

		List<EntityModel<Account>> accounts = accountDao.findAll().stream() //
				.map(accountAssembler::toModel) //
				.collect(Collectors.toList());

		return CollectionModel.of(accounts, linkTo(methodOn(AccountController.class).all()).withSelfRel());
	}

	@GetMapping("/accounts/{accountId}")
	EntityModel<Account> one(@PathVariable Long accountId) {
		Account account = accountDao.findById(accountId) //
				.orElseThrow(() -> new AccountNotFoundException(accountId));

		return accountAssembler.toModel(account);
	}

	@GetMapping("/accounts/{accountId}/transactions")
	EntityModel<Account> accountTransactions(@PathVariable Long accountId) {
		Account account = accountDao.findById(accountId) //
				.orElseThrow(() -> new AccountNotFoundException(accountId));

		return accountAssembler.toModel(account);
	}
}