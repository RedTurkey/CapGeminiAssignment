package com.capgemini.assignment.boosten.web;

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

import com.capgemini.assignment.boosten.dto.CustomerDetailsDTO;
import com.capgemini.assignment.boosten.dto.CustomerPostDTO;
import com.capgemini.assignment.boosten.dto.CustomerPutDTO;
import com.capgemini.assignment.boosten.model.Account;
import com.capgemini.assignment.boosten.model.Customer;
import com.capgemini.assignment.boosten.model.Transaction;

import lombok.AllArgsConstructor;
import services.CustomerServices;

/**
 * The main and only controller since most data relate around the customer,
 * albeit there was supposed to be an account controller and a transaction
 * controller, purely to have their individual data easily accessible without
 * having to send the customer id every time
 */
@RestController
@AllArgsConstructor
public class CustomerController {
	private final CustomerServices customerServices;

	private final CustomerModelAssembler customerAssembler;
	private final TransactionModelAssembler transactionAssembler;
	private final AccountModelAssembler accountAssembler;

	@GetMapping("/customers")
	CollectionModel<EntityModel<Customer>> all() {

		List<EntityModel<Customer>> customers = customerServices.getAllCustomers().stream()
				.map(customerAssembler::toModel).collect(Collectors.toList());

		return CollectionModel.of(customers, linkTo(methodOn(CustomerController.class).all()).withSelfRel());
	}

	@PostMapping("/customers")
	ResponseEntity<?> newCustomer(@RequestBody CustomerPostDTO dto) {
		EntityModel<Customer> entityModel = customerAssembler.toModel(customerServices.createCustomer(dto.getName(), dto.getSurname()));

		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}

	@GetMapping("/customers/{customerId}")
	EntityModel<Customer> one(@PathVariable Long customerId) {
		return customerAssembler.toModel(customerServices.getOneCustomer(customerId));
	}

	@GetMapping("/customers/{customerId}/details")
	CustomerDetailsDTO customerDetails(@PathVariable Long customerId) {
		return customerServices.getCustomerDetails(customerId);
	}

	@PutMapping("/customers/{customerId}")
	ResponseEntity<?> replaceCustomer(@RequestBody CustomerPutDTO dto, @PathVariable Long customerId) {
		EntityModel<Customer> entityModel = customerAssembler.toModel(customerServices.updateCustomer(customerId, dto.getName(), dto.getSurname()));

		return ResponseEntity //
				.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
				.body(entityModel);
	}

	@GetMapping("/customers/{customerId}/accounts")
	CollectionModel<EntityModel<Account>> customerAccounts(@PathVariable Long customerId) {
		List<EntityModel<Account>> accounts = customerServices.getCustomerAccounts(customerId).stream() //
				.map(accountAssembler::toModel) //
				.collect(Collectors.toList());

		return CollectionModel.of(accounts,
				linkTo(methodOn(CustomerController.class).customerAccounts(customerId)).withSelfRel());
	}

	@GetMapping("/customers/{customerId}/transactions")
	CollectionModel<EntityModel<Transaction>> customerTransactions(@PathVariable Long customerId) {
		List<EntityModel<Transaction>> transactionModels = customerServices.getCustomerTransactions(customerId).stream() //
				.map(transactionAssembler::toModel) //
				.collect(Collectors.toList());

		return CollectionModel.of(transactionModels,
				linkTo(methodOn(CustomerController.class).customerTransactions(customerId)).withSelfRel());
	}
}
