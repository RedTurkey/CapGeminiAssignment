package com.capgemini.assignment.boosten.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Transaction {
	private @Id @GeneratedValue Long id;
	private double amount;
	private String communication;
	private Timestamp date;
	
	@JsonIdentityInfo( generator = ObjectIdGenerators.PropertyGenerator.class, property = "id" )
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "FKCreator")
	private Account creator;

	@JsonIdentityInfo( generator = ObjectIdGenerators.PropertyGenerator.class, property = "id" )
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "FKSender")
	private Account sender;

	@JsonIdentityInfo( generator = ObjectIdGenerators.PropertyGenerator.class, property = "id" )
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "FKReceiver")
	private Account receiver;

	public Transaction(double amount, String communication,
			Account creator, Account sender, Account receiver) {
		this.amount = amount;
		this.communication = communication;
		this.creator = creator;
		this.sender = sender;
		this.receiver = receiver;
	}
}
