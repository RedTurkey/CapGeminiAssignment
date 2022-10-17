package com.capgemini.assignment.boosten.dto;

import java.util.Collection;

import com.capgemini.assignment.boosten.model.Transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * A data transfer object to output the "details" of a customer, which is its name, surname, total balance and all its transactions
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDetailsDTO {
	@NonNull
	private String name;
	@NonNull
	private String surname;
	private double balance;
	@NonNull
	private Collection<Transaction> transactions;
}
