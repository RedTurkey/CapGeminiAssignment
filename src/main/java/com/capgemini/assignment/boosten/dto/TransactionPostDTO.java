package com.capgemini.assignment.boosten.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionPostDTO {
	@NonNull
	private Long creatorId;
	@NonNull
	private Long senderId;
	@NonNull
	private Long receiverId;
	private double amount;
	@NonNull
	private String communication;
}
