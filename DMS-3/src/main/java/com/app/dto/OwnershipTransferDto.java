package com.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OwnershipTransferDto {
	
	private String currentOwnerUsername;
	private String newOwnerUsername;

}
