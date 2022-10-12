package com.capgemini.assignment.boosten.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.capgemini.assignment.boosten.model.Customer;

public interface ICustomerDAO extends JpaRepository<Customer, Long> {

	@Query(value = "SELECT c FROM Customer c LEFT JOIN FETCH c.accounts")
	List<Customer> findAllWithAccounts();
}
