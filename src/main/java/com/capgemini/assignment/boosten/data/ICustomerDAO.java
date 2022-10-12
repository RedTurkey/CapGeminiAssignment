package com.capgemini.assignment.boosten.data;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capgemini.assignment.boosten.model.Customer;

public interface ICustomerDAO extends JpaRepository<Customer, Long> {

}
