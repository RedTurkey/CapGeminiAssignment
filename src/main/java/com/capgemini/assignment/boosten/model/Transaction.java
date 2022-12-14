package com.capgemini.assignment.boosten.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * The entity for transaction, which has no setter as the value for a transaction should obviously not change after its creation
 */
@Entity
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class Transaction {
	@Getter
	private @Id @GeneratedValue Long id;
	@Getter
	private double amount;
	@Getter
	private String communication;
	@Getter
	private Timestamp date;
	
	/**
	 * We fetch the following relations eagerly as there is no list in there
	 */
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "FKCreator")
	private Account creator;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "FKSender")
	private Account sender;

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
		this.date = new Timestamp(System.currentTimeMillis());
	}
	
	public Long getCreatorId() {
		return creator.getId();
	}
	
	public Long getSenderId() {
		return sender.getId();
	}
	
	public Long getReceiverId() {
		return receiver.getId();
	}
}
