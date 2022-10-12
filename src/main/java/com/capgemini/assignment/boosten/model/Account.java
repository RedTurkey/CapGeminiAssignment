package com.capgemini.assignment.boosten.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Account {
	private @Id @GeneratedValue Long id;
	private double balance;
	
	@JsonIdentityInfo( generator = ObjectIdGenerators.PropertyGenerator.class, property = "id" )
	@ManyToOne(optional = false)
	@JoinColumn(name = "FKCustomer")
	@Getter
	private Customer customer;

	public Account(Customer customer) {
		this.balance = 0.0;
		this.customer = customer;
	}

	public Account(double balance) {
		this.balance = balance;
	}
	
	@Override
	public String toString() {
		return "Account(id=" + id + ", balance=" + balance + ", customerId=" + customer.getId() + ")";
	}
}