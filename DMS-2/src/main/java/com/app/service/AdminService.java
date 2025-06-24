package com.app.service;

import java.util.List;

import com.app.dto.UserDto;
import com.app.dto.UserUpdateDto;
import com.app.entity.User;

public interface AdminService {
	
	public User addAdmin(UserDto userDto);
	
	public List<User> getAllAdmin();
	
	public User getAdmin(String id);
	
	public boolean deleteAdmin(String id);
	
	public User updateAdmin(String id, UserUpdateDto userUpdateDto);

}
