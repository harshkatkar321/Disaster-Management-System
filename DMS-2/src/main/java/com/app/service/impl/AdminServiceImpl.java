package com.app.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.dto.UserDto;
import com.app.dto.UserUpdateDto;
import com.app.entity.Role;
import com.app.entity.User;
import com.app.repository.UserRepository;
import com.app.service.AdminService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public User addAdmin(UserDto userDto) {
		String hashedPassword = bCryptPasswordEncoder.encode(userDto.getPassword());
		String userId = UUID.randomUUID().toString();
		
		User user = new User();
		user.setId(userId);
		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
		user.setEmail(userDto.getEmail());
		user.setPassword(hashedPassword);
		user.setLocation(userDto.getLocation());
		user.setPhoneNumber(userDto.getPhoneNumber());
		user.setProfilePicture(userDto.getProfilePicture());
		user.setRole(Role.ADMIN);
			
		return userRepository.save(user);
	}

	@Override
	public List<User> getAllAdmin() {
		// TODO Auto-generated method stub
//		return userRepository.findAdmins();
		 return userRepository.findAllByRole(Role.ADMIN);
	}

	@Override
	public User getAdmin(String id) {
		// TODO Auto-generated method stub
		return userRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Admin not found with id : "+id));
	}

	@Override
	public boolean deleteAdmin(String id) {
		// TODO Auto-generated method stub
		User user = userRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Admin not found with id : "+id));
		userRepository.delete(user);
		return true;
	}

	@Override
	public User updateAdmin(String id, UserUpdateDto userUpdateDto) {
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
		existingUser.setRole(Role.ADMIN);
		
		return userRepository.save(existingUser);
	}

}
