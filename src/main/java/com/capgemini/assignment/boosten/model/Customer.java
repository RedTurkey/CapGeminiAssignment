package com.capgemini.assignment.boosten.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Customer {
	private @Id @GeneratedValue Long id;
	private String name;
	private String surname;
	
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
	private Collection<Account> accounts = new ArrayList<>();
	
	public void createAccount() {
		Account account = new Account(this);
		this.addAccount(account);
	}
	
	public void addAccount(Account account) {
		accounts.add(account);
	}

	public Customer(String name, String surname) {
		this.name = name;
		this.surname = surname;
	}
	
	public double getBalance() {
		double balance = 0.0;
		
		for (Account account : accounts) {
			balance = balance + account.getBalance();
		}
		
		return balance;
	}
}
