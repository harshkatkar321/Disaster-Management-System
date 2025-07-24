package com.app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DisasterDto {
	
	@NotBlank(message = "Disaster type is required")
	private String type;
	
	@NotBlank(message = "Location is required")
	private String location;
	
	@NotBlank(message = "Description is required")
	private String description;
	
//	@NotBlank(message = "Status is required")
	private String status;
	
	public double lat;
	public double lng;
	
	@NotNull(message = "User Id is required")
	private String username;

}
