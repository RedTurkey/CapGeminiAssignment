package com.capgemini.assignment.boosten.web;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.assignment.boosten.data.ICustomerDAO;
import com.capgemini.assignment.boosten.exception.CustomerNotFoundException;
import com.capgemini.assignment.boosten.model.Customer;

@RestController
public class CustomerController {
	  private final ICustomerDAO dao;

	  private final CustomerModelAssembler customerAssembler;

	  CustomerController(ICustomerDAO dao, CustomerModelAssembler customerAssembler) {

	    this.dao = dao;
	    this.customerAssembler = customerAssembler;
	  }

	  @GetMapping("/customers")
	  CollectionModel<EntityModel<Customer>> all() {

	    List<EntityModel<Customer>> customers = dao.findAllWithAccounts().stream() //
	        .map(customerAssembler::toModel) //
	        .collect(Collectors.toList());

	    return CollectionModel.of(customers, linkTo(methodOn(CustomerController.class).all()).withSelfRel());
	  }

	  @PostMapping("/customers")
	  ResponseEntity<?> newCustomer(@RequestBody Customer newCustomer) {

	    EntityModel<Customer> entityModel = customerAssembler.toModel(dao.save(newCustomer));

	    return ResponseEntity //
	        .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
	        .body(entityModel);
	  }
	  
	  @GetMapping("/customers/{id}")
	  EntityModel<Customer> one(@PathVariable Long id) {

		  Customer customer = dao.findById(id) //
	        .orElseThrow(() -> new CustomerNotFoundException(id));

	    return customerAssembler.toModel(customer);
	  }

	  @PutMapping("/customers/{id}")
	  ResponseEntity<?> replaceCustomer(@RequestBody Customer newCustomer, @PathVariable Long id) {

		  Customer updatedCustomer = dao.findById(id) //
	        .map(customer -> {
	        	customer.setName(newCustomer.getName());
	          customer.setSurname(newCustomer.getSurname());
	          customer.setAccounts(newCustomer.getAccounts());
	          return dao.save(customer);
	        }) //
	        .orElseGet(() -> {
	        	newCustomer.setId(id);
	          return dao.save(newCustomer);
	        });

	    EntityModel<Customer> entityModel = customerAssembler.toModel(updatedCustomer);

	    return ResponseEntity //
	        .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
	        .body(entityModel);
	  }

	  @DeleteMapping("/customers/{id}")
	  ResponseEntity<?> deleteCustomer(@PathVariable Long id) {

	    dao.deleteById(id);

	    return ResponseEntity.noContent().build();
	  }
}
