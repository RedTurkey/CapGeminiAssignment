package com.capgemini.assignment.boosten.exception;

/**
 * An unfortunately unused exception that was supposed to trigger when an account was tried on a deactivated customer
 */
public class CustomerDeactivatedException extends RuntimeException {

	public CustomerDeactivatedException(Long id) {
		super("Customer " + id + " is deactivated");
	}
}