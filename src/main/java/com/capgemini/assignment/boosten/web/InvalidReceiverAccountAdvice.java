package com.capgemini.assignment.boosten.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.capgemini.assignment.boosten.exception.InvalidReceiverAccountException;

@ControllerAdvice
public class InvalidReceiverAccountAdvice {

	@ResponseBody
	@ExceptionHandler(InvalidReceiverAccountException.class)
	@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
	public String invalidReceiverAccountHandler(InvalidReceiverAccountException ex) {
		return ex.getMessage();
	}
}