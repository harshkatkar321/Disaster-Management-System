package com.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OwnershipTransferDto {
	
	private String currentOwnerId;
	
	private String newOwnerId;

}
