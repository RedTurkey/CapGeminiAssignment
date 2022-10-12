package com.capgemini.assignment.boosten.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class Transaction {
	private @Id @GeneratedValue Long id;
	private double amount;
	private String communication;
	private Timestamp date;
	
	@ManyToOne
	@JoinColumn(name = "FKCreator")
	private Account creator;
	
	@ManyToOne
	@JoinColumn(name = "FKSender")
	private Account sender;

	@ManyToOne
	@JoinColumn(name = "FKReceiver")
	private Account receiver;

}
