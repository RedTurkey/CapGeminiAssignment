package com.capgemini.assignment.boosten.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Customer {
	private @Id @GeneratedValue Long id;
	private String name;
	private String surname;

	public Customer() {
	}

	public Customer(String name, String surname) {

		this.name = name;
		this.surname = surname;
	}
}
