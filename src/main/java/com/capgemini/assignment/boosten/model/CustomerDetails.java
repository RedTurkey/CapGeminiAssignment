package com.capgemini.assignment.boosten.model;

import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerDetails {
	private String name;
	private String surname;
	private double balance;
	private Collection<Transaction> transactions;
}
