package com.capgemini.assignment.boosten.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class AccountPostDTO {
	@NonNull
	private Long customerId;
	private double initialCredit;
}
