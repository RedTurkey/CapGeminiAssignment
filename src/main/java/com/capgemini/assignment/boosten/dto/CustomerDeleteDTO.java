package com.capgemini.assignment.boosten.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class CustomerDeleteDTO {
	@NonNull
	private Long receiverId;
}
