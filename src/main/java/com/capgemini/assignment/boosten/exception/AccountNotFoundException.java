package com.capgemini.assignment.boosten.exception;

public class AccountNotFoundException extends RuntimeException {

	public AccountNotFoundException(Long id) {
		super("Could not find account " + id);
	}
}