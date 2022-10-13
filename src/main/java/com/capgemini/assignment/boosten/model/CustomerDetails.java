package com.capgemini.assignment.boosten.model;

import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * A data transfer object to output the "details" of a customer, which is its name, surname, total balance and all its transactions
 */
@Data
@AllArgsConstructor
public class CustomerDetails {
	private String name;
	private String surname;
	private double balance;
	private Collection<Transaction> transactions;
}
