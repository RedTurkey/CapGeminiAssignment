package com.capgemini.assignment.boosten.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Account {
	private @Id @GeneratedValue Long id;
	private double balance;

	public Account() {
		this.balance = 0.0;
	}

	public Account(double balance) {
		this.balance = balance;
	}
}