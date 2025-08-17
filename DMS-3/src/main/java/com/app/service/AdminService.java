package com.app.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.app.dto.RegisterUserDto;
import com.app.dto.UpdatUserDto;
import com.app.entity.Admin;
import com.app.entity.User;

public interface AdminService {
	
	Admin addUser(RegisterUserDto dto, MultipartFile imageFile);
	
	public List<Admin> getAllAdmin();
	
	public Admin getAdmin(String id);
	
	public boolean deleteAdmin(String id);
	
	public Admin updateAdmin(String id, UpdatUserDto userUpdateDto, MultipartFile imageFile);
	
	void sendMailByCity(String city);

}
