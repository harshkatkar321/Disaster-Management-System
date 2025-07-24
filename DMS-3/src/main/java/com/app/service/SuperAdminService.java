package com.app.service;

import org.springframework.web.multipart.MultipartFile;

import com.app.dto.UpdatUserDto;
import com.app.entity.User;

public interface SuperAdminService {
	
	public  User updateUser(String id, UpdatUserDto userUpdateDto, MultipartFile imageFile);

}
