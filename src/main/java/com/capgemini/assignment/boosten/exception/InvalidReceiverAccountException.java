package com.capgemini.assignment.boosten.exception;

public class InvalidReceiverAccountException extends RuntimeException {

	public InvalidReceiverAccountException(Long id) {
		super("The account " + id + " belong to the customer being deactivated");
	}
}