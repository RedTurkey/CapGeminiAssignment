package com.capgemini.assignment.boosten.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.capgemini.assignment.boosten.exception.CustomerDeactivatedException;

@ControllerAdvice
public class CustomerDeactivatedAdvice {

	@ResponseBody
	@ExceptionHandler(CustomerDeactivatedException.class)
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	public String CustomerDeactivatedHandler(CustomerDeactivatedException ex) {
		return ex.getMessage();
	}
}