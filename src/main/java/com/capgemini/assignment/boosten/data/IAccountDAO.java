package com.capgemini.assignment.boosten.data;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capgemini.assignment.boosten.model.Account;

public interface IAccountDAO extends JpaRepository<Account, Long> {

}
