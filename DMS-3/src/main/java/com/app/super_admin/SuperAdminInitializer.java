package com.app.super_admin;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.app.entity.SuperAdmin;
import com.app.entity.User;
import com.app.enums.Role;
import com.app.repository.SuperAdminRepository;
import com.app.repository.UserRepository;

import jakarta.annotation.PostConstruct;

@Configuration
public class SuperAdminInitializer {
	
	@Value("${app.super-admin-init.enabled}")
	private boolean initEnabled;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private SuperAdminRepository superAdminRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@PostConstruct
	public void initSuperAdmin() {
		if(!initEnabled)
			return ;
		
		boolean exists = superAdminRepository.existsByRole(Role.SUPER_ADMIN);
		
		if(!exists) {
			
			SuperAdmin superAdmin = new SuperAdmin();
			
//			User superAdmin = new User();
			
			String hashedPassword = passwordEncoder.encode("SuperAdmin@123");
			String userId = UUID.randomUUID().toString();
			
			superAdmin.setId(userId);
			superAdmin.setFirstName("Balaji");
			superAdmin.setLastName("Pawar");
			superAdmin.setUsername("balajipawar9259@gmail.com");
			superAdmin.setPassword(hashedPassword);
			superAdmin.setPhoneNumber("9309590511");
			superAdmin.setCity("Pune");
			superAdmin.setRole(Role.SUPER_ADMIN);
			
			superAdminRepository.save(superAdmin);
			
			System.out.println("Super Admin Created Successfully with email Id : "+superAdmin.getUsername());
		}
		
		
	}

}
