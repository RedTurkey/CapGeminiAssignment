package com.capgemini.assignment.boosten.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@EqualsAndHashCode
public class Account {
	@Getter
	private @Id @GeneratedValue Long id;
	@Getter
	@Setter
	private double balance;
	@Getter
	@Setter
	private AccountStatus status;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "FKCustomer")
	private Customer customer;

	@OneToMany(mappedBy = "creator", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Collection<Transaction> createdTransactions = new ArrayList<>();

	@OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Collection<Transaction> sendTransactions = new ArrayList<>();

	@OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Collection<Transaction> receivedTransactions = new ArrayList<>();

	public Account(Customer customer) {
		this.balance = 0.0;
		this.customer = customer;
		this.status = AccountStatus.OPEN;
	}
	
	public Collection<Transaction> getTransactions() {
		Collection<Transaction> transactions = new ArrayList<>();

		for(Transaction transaction : sendTransactions) {
			transactions.add(transaction);
		}

		for(Transaction transaction : receivedTransactions) {
			transactions.add(transaction);
		}
		
		return transactions;
	}
	
	public Long getCustomerId() {
		return customer.getId();
	}
	
	@Override
	public String toString() {
		return "Account(id=" + id + ", balance=" + balance + ", customerId=" + customer.getId() + ")";
	}
}