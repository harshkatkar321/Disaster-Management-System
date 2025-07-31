package com.app.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DisasterDto {
	
	private String id;
	
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
	private String user_id;
	
	private String imageData;
	private String imageName;
	private String imageType;
	

}
