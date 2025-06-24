package com.app.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Disaster {
	
	@Id
	private String id;
	
//	@NotBlank(message = "Disaster type is required")
	private String type;
	
//	@NotBlank(message = "Location is required")
	private String location;
	
//	@NotBlank(message = "Description is required")
	private String description;
	
//	@NotBlank(message = "Description is required")
	private String image;
	
//	@NotBlank(message = "Status is required")
	private String status;
	
//	@NotNull(message = "Date is required")
	private Date date;
	
//	@NotBlank(message = "Description is required")
	@Column(length = 2000)
	private String mapLocation;
	
	private boolean isActive = true ;
	
	private boolean isVerified = false;
	
	@ManyToOne
	@JoinColumn(name = "user_id")  // ← explicitly specify the desired column name
	@JsonIgnore
	private User user;

}
