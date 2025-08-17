package com.app.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
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
	
//	@NotBlank(message = "image is required")
	private String imageName;
	private String imageType;
	@Lob
	private byte[] imageData;
	
//	@NotBlank(message = "Status is required")
	private String status;
	
	@CreationTimestamp
	@Column(nullable = false, updatable = false)
	private LocalDateTime Date;
	
	@Column(columnDefinition = "POINT SRID 4326")
	private org.locationtech.jts.geom.Point mapLocation;
	
	private boolean isActive = true ;
	
	private boolean isVerified = false;
	
	@ManyToOne
	@JoinColumn(name = "user_id")  // ← explicitly specify the desired column name
	@JsonIgnore
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "admin_id")  // ← explicitly specify the desired column name
	@JsonIgnore
	private Admin admin;
	
	@ManyToOne
	@JoinColumn(name = "super_admin_id")  // ← explicitly specify the desired column name
	@JsonIgnore
	private SuperAdmin superAdmin;
	
	

}

