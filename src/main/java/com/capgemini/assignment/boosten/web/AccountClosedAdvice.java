package com.capgemini.assignment.boosten.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.capgemini.assignment.boosten.exception.AccountClosedException;

@ControllerAdvice
public class AccountClosedAdvice {

	@ResponseBody
	@ExceptionHandler(AccountClosedException.class)
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	public String AccountClosedHandler(AccountClosedException ex) {
		return ex.getMessage();
	}
}