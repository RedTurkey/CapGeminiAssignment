package com.capgemini.assignment.boosten.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.capgemini.assignment.boosten.exception.CustomerNotFoundException;

@ControllerAdvice
public class CustomerNotFoundAdvice {

  @ResponseBody
  @ExceptionHandler(CustomerNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public String customerNotFoundHandler(CustomerNotFoundException ex) {
    return ex.getMessage();
  }
}