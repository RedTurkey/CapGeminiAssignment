package com.capgemini.assignment.boosten.exception;

/**
 * An unfortunately unused exception that was supposed to trigger when a transaction was tried on a closed account
 */
public class AccountClosedException extends RuntimeException {

	public AccountClosedException(Long id) {
		super("Account " + id + " is closed");
	}
}
