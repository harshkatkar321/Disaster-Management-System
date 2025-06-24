package com.app.service;

import com.app.dto.UserUpdateDto;
import com.app.entity.User;

public interface SuperAdminService {
	
	public  User updateUser(String id, UserUpdateDto userUpdateDto);

}
