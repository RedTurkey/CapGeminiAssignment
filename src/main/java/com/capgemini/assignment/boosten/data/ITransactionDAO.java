package com.capgemini.assignment.boosten.data;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capgemini.assignment.boosten.model.Transaction;

public interface ITransactionDAO extends JpaRepository<Transaction, Long> {

}
