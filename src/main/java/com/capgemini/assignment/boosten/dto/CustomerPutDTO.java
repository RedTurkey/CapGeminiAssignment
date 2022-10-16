package com.capgemini.assignment.boosten.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class CustomerPutDTO {
	@NonNull
	private String name;
	@NonNull
	private String surname;
}
