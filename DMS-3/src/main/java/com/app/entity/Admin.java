package com.app.entity;

import java.util.ArrayList;
import java.util.List;

import com.app.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Admin {
	
	@Id
	private String Id;

	private String firstName;
	
	private String lastName;
	
	private String phoneNumber;
	
	@Column(nullable = false, unique = true)
	private String username;
	
	private String password;
	
	private String city;
	
	private String profilePicture;
	
	private String imageType;
	@Lob
	private byte[] imageData;
	
//	//disaster mapping
    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Disaster> disasters = new ArrayList<>();
    
    @Enumerated(EnumType.STRING)
    private Role role;
    
    @OneToMany(mappedBy = "admin")
    @JsonIgnore
    private List<Alert> alerts;
}
