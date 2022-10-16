package com.capgemini.assignment.boosten.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capgemini.assignment.boosten.model.Transaction;

@Repository
public interface ITransactionDAO extends JpaRepository<Transaction, Long> {
}
