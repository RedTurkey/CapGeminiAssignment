package com.capgemini.assignment.boosten.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.capgemini.assignment.boosten.model.Transaction;

public interface ITransactionDAO extends JpaRepository<Transaction, Long> {
	@Query(value = "SELECT t FROM Transaction t WHERE t.sender=?1")
	List<Transaction> findAllFromSender(Long id);

	@Query(value = "SELECT t FROM Transaction t WHERE t.receiver=?1")
	List<Transaction> findAllFromReceiver(Long id);
	
	@Query(value = "SELECT t FROM Transaction t WHERE t.creator=?1")
	List<Transaction> findAllFromCreator(Long id);
}
