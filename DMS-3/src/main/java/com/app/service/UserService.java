package com.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.entity.Admin;
import com.app.entity.Resource;
import com.app.entity.SuperAdmin;
import com.app.entity.User;
import com.app.enums.Role;
import com.app.repository.AdminRepository;
import com.app.repository.ResourceRepository;
import com.app.repository.SuperAdminRepository;
import com.app.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private SuperAdminRepository superAdminRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired ResourceRepository resourceRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<SuperAdmin> existingSuperAdmin = superAdminRepository.findByUsername(username);
		
		if(existingSuperAdmin.isPresent())
		{
			SuperAdmin superAdmin = existingSuperAdmin.get();
			 // Get the single enum role (e.g., USER or ADMIN)
		    Role roleEnum = superAdmin.getRole(); // your enum type

		    // Build a GrantedAuthority list with proper ROLE_ prefix
		    String roleName = roleEnum.name(); // e.g. "USER"
		    if (!roleName.startsWith("ROLE_")) {
		        roleName = "ROLE_" + roleName;
		    }
		    GrantedAuthority authority = new SimpleGrantedAuthority(roleName);
		    
		    boolean active = superAdmin.isActive();

		    return new org.springframework.security.core.userdetails.User(
		    	superAdmin.getUsername(),
		    	superAdmin.getPassword(),
		    	active,              // enabled
		        true,                // accountNonExpired
		        true,                // credentialsNonExpired
		        true,                // accountNonLocked
		        List.of(authority)
		    );
			
		}
		
		Optional<Admin> existingAdmin = adminRepository.findByUsername(username);
		
		if(existingAdmin.isPresent())
		{
			Admin admin = existingAdmin.get();
			 // Get the single enum role (e.g., USER or ADMIN)
		    Role roleEnum = admin.getRole(); // your enum type

		    // Build a GrantedAuthority list with proper ROLE_ prefix
		    String roleName = roleEnum.name(); // e.g. "USER"
		    if (!roleName.startsWith("ROLE_")) {
		        roleName = "ROLE_" + roleName;
		    }
		    GrantedAuthority authority = new SimpleGrantedAuthority(roleName);
		    
		    boolean active = admin.isActive();

		    return new org.springframework.security.core.userdetails.User(
		    	admin.getUsername(),
		    	admin.getPassword(),
		    	active,              // enabled
		        true,                // accountNonExpired
		        true,                // credentialsNonExpired
		        true,                // accountNonLocked
		        List.of(authority)
		    );
			
		}
		
		Optional<Resource> existingResource = resourceRepository.findByUsername(username);
		
		if(existingResource.isPresent())
		{
			Resource resource = existingResource.get();
			Role roleEnum = resource.getRole();
			
			String roleName = roleEnum.name(); // e.g. "USER"
		    if (!roleName.startsWith("ROLE_")) {
		        roleName = "ROLE_" + roleName;
		    }
		    
		    GrantedAuthority authority = new SimpleGrantedAuthority(roleName);
		    
		    boolean active = resource.isActive();
		    
		    return new org.springframework.security.core.userdetails.User(
		    		resource.getUsername(),
		    		resource.getPassword(),
		    		active,              // enabled
			        true,                // accountNonExpired
			        true,                // credentialsNonExpired
			        true,                // accountNonLocked
			        List.of(authority)
			    );
		}
		
		
	    User user = userRepository.findByUsername(username)
	        .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
	    
	    boolean active = user.isActive();

	    // Get the single enum role (e.g., USER or ADMIN)
	    Role roleEnum = user.getRole(); // your enum type

	    // Build a GrantedAuthority list with proper ROLE_ prefix
	    String roleName = roleEnum.name(); // e.g. "USER"
	    if (!roleName.startsWith("ROLE_")) {
	        roleName = "ROLE_" + roleName;
	    }
	    GrantedAuthority authority = new SimpleGrantedAuthority(roleName);

	    return new org.springframework.security.core.userdetails.User(
	        user.getUsername(),
	        user.getPassword(),
	        active,              // enabled
	        true,                // accountNonExpired
	        true,                // credentialsNonExpired
	        true,                // accountNonLocked
	        List.of(authority)
	    ); 
	}
}
