package com.capgemini.assignment.boosten.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.Getter;

@Entity
@Data
public class Account {
	private @Id @GeneratedValue Long id;
	private double balance;
	
	@ManyToOne
	@JoinColumn(name = "FKCustomer")
	@Getter
	private Customer customer;

	public Account() {
		this.balance = 0.0;
	}

	public Account(double balance) {
		this.balance = balance;
	}
}