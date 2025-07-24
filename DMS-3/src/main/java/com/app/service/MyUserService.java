package com.app.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.app.dto.MailDto;
import com.app.dto.RegisterUserDto;
import com.app.dto.UpdatUserDto;
import com.app.entity.User;

public interface MyUserService {
	
	User saveUser(RegisterUserDto dto);

	User addUser(RegisterUserDto dto, MultipartFile imageFile);
	
	List<User> getUsers();
	
	User getUser(String username);
	
	User updateUser(String id, UpdatUserDto userUpdateDto, MultipartFile imageFile);
	
	void deleteUser(String id);
	
	List<User> findByLocation(String city);
	
	List<String> findPhoneNumberByCity(String city);
	
	List<String> findEmailByCity(String city);
	
	void sendMailByCity(String city, MailDto dto);

}
