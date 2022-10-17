package com.capgemini.assignment.boosten.web;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.afford;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.capgemini.assignment.boosten.model.Customer;
import com.capgemini.assignment.boosten.model.CustomerStatus;

@Component
public class CustomerModelAssembler implements RepresentationModelAssembler<Customer, EntityModel<Customer>> {

	@Override
	public EntityModel<Customer> toModel(Customer customer) {
		EntityModel<Customer> entityModel = EntityModel.of(customer,
				linkTo(methodOn(CustomerController.class).customerAccounts(customer.getId()))
						.withRel("customer's accounts"),
				linkTo(methodOn(CustomerController.class).customerTransactions(customer.getId()))
						.withRel("customer's transactions"),
				linkTo(methodOn(CustomerController.class).customerDetails(customer.getId())).withRel(
						"customer's details"),
				linkTo(methodOn(CustomerController.class).all()).withRel("all"));

		if (customer.getStatus() == CustomerStatus.ACTIVE) {
			entityModel.add(linkTo(methodOn(CustomerController.class).one(customer.getId())).withSelfRel()
					.andAffordance(afford(methodOn(CustomerController.class).updateCustomer(customer.getId(), null))));
			entityModel.add(linkTo(methodOn(CustomerController.class).deactivateCustomer(customer.getId(), null))
					.withRel("deactivate customer"));
		} else {
			entityModel.add(linkTo(methodOn(CustomerController.class).one(customer.getId())).withSelfRel());
		}

		return entityModel;
	}
}