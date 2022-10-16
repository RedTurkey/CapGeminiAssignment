package com.capgemini.assignment.boosten.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class AccountDeleteDTO {
	@NonNull
	private Long receiverId;
}
