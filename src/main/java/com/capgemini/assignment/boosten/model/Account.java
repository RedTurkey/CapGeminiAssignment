package com.capgemini.assignment.boosten.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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

	public Account(Customer customer) {
		this.balance = 0.0;
		this.customer = customer;
	}

	public Account(double balance) {
		this.balance = balance;
	}
	
	public Long getCustomerId() {
		return customer.getId();
	}
	
	@Override
	public String toString() {
		return "Account(id=" + id + ", balance=" + balance + ", customerId=" + customer.getId() + ")";
	}
}