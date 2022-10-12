package com.capgemini.assignment.boosten.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class Customer {
	private @Id @GeneratedValue Long id;
	private String name;
	private String surname;
	
	@OneToMany(mappedBy = "customer")
	private Collection<Account> accounts = new ArrayList<>();

	public Customer() {
	}

	public Customer(String name, String surname) {
		this.name = name;
		this.surname = surname;
	}
}
