package com.capgemini.assignment.boosten.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capgemini.assignment.boosten.model.Account;

@Repository
public interface IAccountDAO extends JpaRepository<Account, Long> {

}
