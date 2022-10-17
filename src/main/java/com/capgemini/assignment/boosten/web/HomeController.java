package com.capgemini.assignment.boosten.web;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.afford;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class HomeController {

	@GetMapping("/")
	List<Link> root() {
		List<Link> links = new ArrayList<>();
		links.add(linkTo(methodOn(CustomerController.class).all()).withRel("all customers")
				.andAffordance(afford(methodOn(CustomerController.class).newCustomer(null))));
		links.add(linkTo(methodOn(AccountController.class).all()).withRel("all accounts")
				.andAffordance(afford(methodOn(AccountController.class).newAccount(null))));
		links.add(linkTo(methodOn(TransactionController.class).all()).withRel("all transactions")
				.andAffordance(afford(methodOn(TransactionController.class).newTransaction(null))));
		return links;
	}

}
