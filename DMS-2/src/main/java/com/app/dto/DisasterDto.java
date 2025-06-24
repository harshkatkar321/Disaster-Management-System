package com.app.dto;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.app.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
	
	@NotBlank(message = "Description is required")
	private String image;
	
	@NotBlank(message = "Status is required")
	private String status;
	
	@NotNull(message = "Date is required")
	private Date date;
	
//	@NotBlank(message = "Description is required")
	private String mapLocation;
	
	private boolean isActive = true ;
	
	private boolean isVerified = false;
	
	@NotNull(message = "User Id is required")
	private String userId;
	

}
