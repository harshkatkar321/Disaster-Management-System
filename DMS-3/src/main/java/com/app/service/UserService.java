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
import com.app.entity.SuperAdmin;
import com.app.entity.User;
import com.app.enums.Role;
import com.app.repository.AdminRepository;
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

//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		
//		User user = userRepository.findByUsername(username)
//				.orElseThrow(()-> new UsernameNotFoundException(username));
//		
//		return new org.springframework.security.core.userdetails.User(
//				user.getUsername(),
//				user.getPassword(),
//				List.of(new SimpleGrantedAuthority("USER"))
//				);
//	}
	
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

		    return new org.springframework.security.core.userdetails.User(
		    	superAdmin.getUsername(),
		    	superAdmin.getPassword(),
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

		    return new org.springframework.security.core.userdetails.User(
		    	admin.getUsername(),
		    	admin.getPassword(),
		        List.of(authority)
		    );
			
		}
		
		
	    User user = userRepository.findByUsername(username)
	        .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

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
	        List.of(authority)
	    );
	}
	
//	public User saveUser(RegisterUserDto dto) {
//		
//		User olduser = userRepository.findByUsername(dto.getEmail()).get();
//		
//		if (olduser != null) {
//            throw new UsernameAlreadyExistsException(dto.getEmail());
//        }
//			
//			String hashedPassword = passwordEncoder.encode(dto.getPassword());
//			String userId = UUID.randomUUID().toString();
//			
//			User user = new User();
//			user.setId(userId);
//			user.setFirstName(dto.getFirstName());
//			user.setLastName(dto.getLastName());
//			user.setUsername(dto.getEmail());
//			user.setPassword(hashedPassword);
//			user.setCity(dto.getCity());
//			user.setPhoneNumber(dto.getPhoneNumber());
//			user.setProfilePicture(dto.getProfilePicture());
//			user.setRole(Role.USER);
//				
//			return userRepository.save(user);
//	}

}
