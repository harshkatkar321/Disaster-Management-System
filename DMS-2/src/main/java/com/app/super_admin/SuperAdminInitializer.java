package com.app.super_admin;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.app.entity.Role;
import com.app.entity.User;
import com.app.repository.UserRepository;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

@Configuration
public class SuperAdminInitializer {
	
	@Value("${app.super-admin-init.enabled}")
	private boolean initEnabled;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@PostConstruct
	public void initSuperAdmin() {
		if(!initEnabled)
			return ;
		
		boolean exists = userRepository.existsByRole(Role.SUPER_ADMIN);
		
		if(!exists) {
			User superAdmin = new User();
			
			String hashedPassword = bCryptPasswordEncoder.encode("SuperAdmin@123");
			String userId = UUID.randomUUID().toString();
			
			superAdmin.setId(userId);
			superAdmin.setFirstName("Balaji");
			superAdmin.setLastName("Pawar");
			superAdmin.setEmail("balajipawar9259@gmail.com");
			superAdmin.setPassword(hashedPassword);
			superAdmin.setPhoneNumber("9309590511");
			superAdmin.setLocation("Pune");
			superAdmin.setRole(Role.SUPER_ADMIN);
			
			userRepository.save(superAdmin);
			
			System.out.println("Super Admin Created Successfully with email Id : "+superAdmin.getEmail());
		}
		
		
	}

}
