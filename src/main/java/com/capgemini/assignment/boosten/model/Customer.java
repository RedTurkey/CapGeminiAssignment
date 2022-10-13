package com.capgemini.assignment.boosten.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Customer {
	@Getter
	private @Id @GeneratedValue Long id;
	@Getter
	@Setter
	private String name;
	@Getter
	@Setter
	private String surname;
	@Getter
	@Setter
	private CustomerStatus status;
	
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Collection<Account> accounts = new ArrayList<>();
	
	public Collection<Long> getAccounts() {
		Collection<Long> accountsId = new ArrayList<>();
		
		for (Account account : accounts) {
			accountsId.add(account.getId());
		}
		
		return accountsId;
	}
	
	public double getBalance() {
		double balance = 0.0;
		
		for (Account account : accounts) {
			balance = balance + account.getBalance();
		}
		
		return balance;
	}
	
	public void addAccount(Account account) {
		accounts.add(account);
	}

	public Customer(String name, String surname) {
		this.name = name;
		this.surname = surname;
		this.status = CustomerStatus.ACTIVE;
	}
}
