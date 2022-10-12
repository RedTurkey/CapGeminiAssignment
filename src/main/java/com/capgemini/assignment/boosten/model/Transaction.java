package com.capgemini.assignment.boosten.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Transaction {
	private @Id @GeneratedValue Long id;
	private double amount;

}
