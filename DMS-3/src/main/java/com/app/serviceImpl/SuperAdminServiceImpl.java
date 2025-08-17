package com.app.serviceImpl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.app.dto.UpdatUserDto;
import com.app.entity.SuperAdmin;
import com.app.entity.User;
import com.app.repository.SuperAdminRepository;
import com.app.repository.UserRepository;
import com.app.service.SuperAdminService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class SuperAdminServiceImpl implements SuperAdminService {


	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private SuperAdminRepository superAdminRepository;
	
	@Override
	public User updateUser(String id, UpdatUserDto userUpdateDto, MultipartFile imageFile) {
		// TODO Auto-generated method stub
		String password = userUpdateDto.getPassword();
		String hashedPassword = passwordEncoder.encode(password);
		User existingUser = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User Not Found"));
		
		if(!passwordEncoder.matches(password, existingUser.getPassword()))
		{
			existingUser.setPassword(hashedPassword);
		}
		existingUser.setCity(userUpdateDto.getCity());
		existingUser.setPhoneNumber(userUpdateDto.getPhoneNumber());
		
		 if (imageFile != null && !imageFile.isEmpty()) {
			 
			 existingUser.setProfilePicture(imageFile.getOriginalFilename());
			 existingUser.setImageType(imageFile.getContentType());
		        try {
		        	existingUser.setImageData(imageFile.getBytes());
		        } catch (IOException e) {
		            throw new RuntimeException("Failed to process image", e);
		        }
		    }
		
		return userRepository.save(existingUser);
	}

	@Override
	public SuperAdmin getSuperAdmin(String username) {
		// TODO Auto-generated method stub
		return superAdminRepository.findByUsername(username).get();
	}

}
