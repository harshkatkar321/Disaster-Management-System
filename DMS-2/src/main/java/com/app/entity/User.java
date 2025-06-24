package com.app.entity;

import java.util.ArrayList;
import java.util.List;


import jakarta.persistence.CascadeType;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
	
	@Id
	private String Id;

	private String firstName;
	
	private String lastName;
	
	private String phoneNumber;
	
	private String email;
	
	private String password;
	
	private String location;
	
	private String profilePicture;
	
	//disaster mapping
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Disaster> disasters = new ArrayList<>();
    
    private Role role;

}
