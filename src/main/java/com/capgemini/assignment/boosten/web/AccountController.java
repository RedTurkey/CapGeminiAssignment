package com.capgemini.assignment.boosten.web;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.assignment.boosten.dto.AccountDeleteDTO;
import com.capgemini.assignment.boosten.dto.AccountPostDTO;
import com.capgemini.assignment.boosten.model.Account;
import com.capgemini.assignment.boosten.model.Transaction;

import lombok.AllArgsConstructor;
import services.AccountServices;

@RestController
@AllArgsConstructor
public class AccountController {
	private final AccountServices accountServices;

	private final AccountModelAssembler accountAssembler;
	private final TransactionModelAssembler transactionAssembler;

	@GetMapping("/accounts")
	CollectionModel<EntityModel<Account>> all() {
		List<EntityModel<Account>> accounts = accountServices.getAllAccounts().stream() //
				.map(accountAssembler::toModel) //
				.collect(Collectors.toList());

		return CollectionModel.of(accounts, linkTo(methodOn(AccountController.class).all()).withSelfRel());
	}

	@PostMapping("/accounts")
	EntityModel<Account> newAccount(@RequestBody AccountPostDTO newAccountDTO) {
		return accountAssembler.toModel(
				accountServices.createAccount(newAccountDTO.getCustomerId(), newAccountDTO.getInitialCredit()));
	}

	@GetMapping("/accounts/{accountId}")
	EntityModel<Account> one(@PathVariable Long accountId) {
		return accountAssembler.toModel(accountServices.getOneAccount(accountId));
	}

	@DeleteMapping("/accounts/{accountId}/close")
	ResponseEntity<?> closeAccount(@PathVariable Long accountId, @RequestBody AccountDeleteDTO dto) {
		accountServices.closeAccount(accountId, dto.getReceiverId());

		return ResponseEntity.noContent().build();
	}

	@GetMapping("/accounts/{accountId}/transactions")
	CollectionModel<EntityModel<Transaction>> accountTransactions(@PathVariable Long accountId) {
		List<EntityModel<Transaction>> transactions = accountServices.getAccountTransactions(accountId).stream()
				.map(transactionAssembler::toModel).collect(Collectors.toList());

		return CollectionModel.of(transactions,
				linkTo(methodOn(AccountController.class).accountTransactions(accountId)).withSelfRel());
	}
}