package com.capgemini.assignment.boosten.exception;

public class AccountClosedException extends RuntimeException {

	public AccountClosedException(Long id) {
		super("Account " + id + " is closed");
	}
}
