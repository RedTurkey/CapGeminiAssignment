package com.capgemini.assignment.boosten.exception;

public class CustomerDeactivatedException extends RuntimeException {

	public CustomerDeactivatedException(Long id) {
		super("Customer " + id + " is deactivated");
	}
}