package com.app.service;

import java.util.List;

import com.app.dto.UserDto;
import com.app.dto.UserUpdateDto;
import com.app.entity.User;

public interface UserService {
	
	public List<User> getUsers();
	
	public User getUser(String id);

	public User addUser(UserDto userDto);
	
	public  User updateUser(String id, UserUpdateDto userUpdateDto);
	
	public void deleteUser(String id);
	
	public boolean authenticate(String email, String password);
	
	public List<User> findByLocation(String location);
	
}
