package com.app.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.dto.UserDto;
import com.app.dto.UserUpdateDto;
import com.app.entity.Role;
import com.app.entity.User;
import com.app.repository.UserRepository;
import com.app.service.UserService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public List<User> getUsers() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}

	@Override
	public User getUser(String id) {
		// TODO Auto-generated method stub
		return userRepository.findById(id).get();
	}

	@Override
	public User addUser(UserDto userDto) {
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
		user.setRole(Role.USER);
			
		return userRepository.save(user);
	}

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

	@Override
	public void deleteUser(String id) {
		// TODO Auto-generated method stub
		User user = userRepository.findById(id).get();
		
		if(user != null)
			userRepository.delete(user);
	}

	@Override
	public boolean authenticate(String email, String password) {
		// TODO Auto-generated method stub
		User user = userRepository.findByEmail(email);
		
		if(user == null)
		{
			throw new UsernameNotFoundException("User does not exist in the database");
		}
		if(!bCryptPasswordEncoder.matches(password, user.getPassword()))
		{
			throw new BadCredentialsException("The Username or password is incorrect");
		}
		return true;
	}

	@Override
	public List<User> findByLocation(String location) {
		// TODO Auto-generated method stub
		return userRepository.findByLocationContainingIgnoreCase(location);
	}

}
