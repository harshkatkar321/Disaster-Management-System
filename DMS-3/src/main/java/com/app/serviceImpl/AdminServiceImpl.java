package com.app.serviceImpl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.app.dto.RegisterUserDto;
import com.app.dto.UpdatUserDto;
import com.app.entity.Admin;
import com.app.entity.SuperAdmin;
import com.app.entity.User;
import com.app.enums.Role;
import com.app.exception.UsernameAlreadyExistsException;
import com.app.repository.AdminRepository;
import com.app.repository.SuperAdminRepository;
import com.app.repository.UserRepository;
import com.app.service.AdminService;
import com.app.service.EmailService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private EmailService emailService;
	
	
	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private SuperAdminRepository superAdminRepository;

	@Override
	public Admin addUser(RegisterUserDto dto, MultipartFile imageFile) {
		// TODO Auto-generated method stub
		Optional<SuperAdmin> existring2 = superAdminRepository.findByUsername(dto.getEmail());
		
		Optional<User> existing1 = userRepository.findByUsername(dto.getEmail());
		
		Optional<Admin> existing = adminRepository.findByUsername(dto.getEmail());
		
		if (existing.isPresent() || existing1.isPresent() || existring2.isPresent()) {
		    throw new UsernameAlreadyExistsException(dto.getEmail());
		}
		
		String hashedPassword = passwordEncoder.encode(dto.getPassword());
		String userId = UUID.randomUUID().toString();
		
		Admin admin = new Admin();
		admin.setId(userId);
		admin.setFirstName(dto.getFirstName());
		admin.setLastName(dto.getLastName());
		admin.setUsername(dto.getEmail());
		admin.setPassword(hashedPassword);
		admin.setCity(dto.getCity());
		admin.setPhoneNumber(dto.getPhoneNumber());
		admin.setRole(Role.ADMIN);
		
		 if (imageFile != null && !imageFile.isEmpty()) {
			 
			 admin.setProfilePicture(imageFile.getOriginalFilename());
			 admin.setImageType(imageFile.getContentType());
		        try {
		        	admin.setImageData(imageFile.getBytes());
		        } catch (IOException e) {
		            throw new RuntimeException("Failed to process image", e);
		        }
		    }
		
		return adminRepository.save(admin);
	}

	@Override
	public List<Admin> getAllAdmin() {
		// TODO Auto-generated method stub
		return adminRepository.findAll();
	}

	@Override
	public Admin getAdmin(String id) {
		// TODO Auto-generated method stub
		Optional<Admin> admin = adminRepository.findById(id);
		if(admin.isPresent()) {
			return admin.get();
		}
		else {
			return null;
		}
	}

	@Override
	public boolean deleteAdmin(String id) {
		
		Optional<Admin> admin = adminRepository.findById(id);
		if(admin.isPresent()) {
			adminRepository.delete(admin.get());
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public Admin updateAdmin(String id, UpdatUserDto userUpdateDto, MultipartFile imageFile) {
		String password = userUpdateDto.getPassword();
		String hashedPassword = passwordEncoder.encode(password);
		Admin existingUser = adminRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User Not Found"));
		
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
		
		return adminRepository.save(existingUser);
	}

	@Override
	public void sendMailByCity(String city) {
		// TODO Auto-generated method stub
		
		List<String> mails = userRepository.findUsernameByCityContainingIgnoreCase(city);
		for (String mail : mails) {
			emailService.sendEmail("mail",
					"This is Testing mail From Disaster Management System.",
					"Hi, app kaise hai ?");
			
		}
	}

}
