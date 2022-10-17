package com.capgemini.assignment.boosten.web;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.afford;
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

import com.capgemini.assignment.boosten.dto.AccountDeleteDTO;
import com.capgemini.assignment.boosten.dto.AccountPostDTO;
import com.capgemini.assignment.boosten.model.Account;
import com.capgemini.assignment.boosten.model.Transaction;
import com.capgemini.assignment.boosten.services.AccountServices;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class AccountController {
	private AccountServices accountServices;

	private AccountModelAssembler accountAssembler;
	private TransactionModelAssembler transactionAssembler;

	@GetMapping("/accounts")
	CollectionModel<EntityModel<Account>> all() {
		List<EntityModel<Account>> accounts = accountServices.getAllAccounts().stream().map(accountAssembler::toModel)
				.collect(Collectors.toList());

		return CollectionModel.of(accounts, linkTo(methodOn(AccountController.class).all()).withSelfRel()
				.andAffordance(afford(methodOn(AccountController.class).newAccount(null))));
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

	@GetMapping("/accounts/{accountId}/transactions")
	CollectionModel<EntityModel<Transaction>> accountTransactions(@PathVariable Long accountId) {
		List<EntityModel<Transaction>> transactions = accountServices.getAccountTransactions(accountId).stream()
				.map(transactionAssembler::toModel).collect(Collectors.toList());

		return CollectionModel.of(transactions,
				linkTo(methodOn(AccountController.class).accountTransactions(accountId)).withSelfRel());
	}

	@PutMapping("/accounts/{accountId}/close")
	ResponseEntity<?> closeAccount(@PathVariable Long accountId, @RequestBody AccountDeleteDTO dto) {
		EntityModel<Account> entityModel = accountAssembler
				.toModel(accountServices.closeAccount(accountId, dto.getReceiverId()));

		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}
}