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

import com.fasterxml.jackson.annotation.JsonIgnore;

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

	/**
	 * The following collections are used to keep track of the different types of transactions for the account
	 */
	
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
	
	@JsonIgnore
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
	
	public Collection<Long> getTransactionsId() {
		Collection<Long> transactionsId = new ArrayList<>();

		for(Transaction transaction : sendTransactions) {
			transactionsId.add(transaction.getId());
		}

		for(Transaction transaction : receivedTransactions) {
			transactionsId.add(transaction.getId());
		}
		
		return transactionsId;
	}
	
	public Long getCustomerId() {
		return customer.getId();
	}
	
	/**
	 * To prevent reference looping, a custom toString method is used, albeit it became obsolete when getting rid of the unnecessary getter for the customer
	 */
	@Override
	public String toString() {
		return "Account(id=" + id + ", balance=" + balance + ", customerId=" + customer.getId() + ")";
	}
}