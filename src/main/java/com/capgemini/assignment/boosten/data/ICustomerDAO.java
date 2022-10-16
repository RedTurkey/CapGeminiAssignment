package com.capgemini.assignment.boosten.data;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.capgemini.assignment.boosten.model.Customer;

@Repository
public interface ICustomerDAO extends JpaRepository<Customer, Long> {

	/**
	 * A query used to retrieve all the customer with their account, since the relation if lazily fetched on the entity
	 */
	@Query(value = "SELECT c FROM Customer c LEFT JOIN FETCH c.accounts")
	List<Customer> findAllWithAccounts();
	
	/**
	 * This is here for the same reason as the above method, but for one account
	 */
	@Query(value = "SELECT c FROM Customer c LEFT JOIN FETCH c.accounts WHERE c.id=?1")
	Optional<Customer> findByIdWithAccounts(Long id);
}
