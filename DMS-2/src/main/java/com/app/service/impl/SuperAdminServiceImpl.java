package com.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.dto.UserUpdateDto;
import com.app.entity.Role;
import com.app.entity.User;
import com.app.repository.UserRepository;
import com.app.service.SuperAdminService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class SuperAdminServiceImpl implements SuperAdminService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public User updateUser(String id, UserUpdateDto userUpdateDto) {
		// TODO Auto-generated method stub
		String password = userUpdateDto.getPassword();
		String hashedPassword = bCryptPasswordEncoder.encode(password);
		User existingUser = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User Not Found"));
		
		existingUser.setFirstName(userUpdateDto.getFirstName());
		existingUser.setLastName(userUpdateDto.getLastName());
		
		if(!bCryptPasswordEncoder.matches(password, existingUser.getPassword()))
		{
			existingUser.setPassword(hashedPassword);
		}
		existingUser.setLocation(userUpdateDto.getLocation());
		existingUser.setPhoneNumber(userUpdateDto.getPhoneNumber());
		existingUser.setProfilePicture(userUpdateDto.getProfilePicture());
		existingUser.setRole(Role.USER);
		
		return userRepository.save(existingUser);
	}

}
