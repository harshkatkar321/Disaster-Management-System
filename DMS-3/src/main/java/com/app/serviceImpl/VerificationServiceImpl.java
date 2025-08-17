package com.app.serviceImpl;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.dto.SetNewPasswordDto;
import com.app.entity.Admin;
import com.app.entity.Resource;
import com.app.entity.User;
import com.app.repository.AdminRepository;
import com.app.repository.ResourceRepository;
import com.app.repository.SuperAdminRepository;
import com.app.repository.UserRepository;
import com.app.service.EmailService;
import com.app.service.SmsService;
import com.app.service.VerificationService;
import com.app.util.OtpUtil;

@Service
public class VerificationServiceImpl implements VerificationService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private SuperAdminRepository superAdminRepository;
	
	@Autowired
	private SmsService smsService;
	
	@Autowired
	private OtpUtil otpUtil;
	
	@Autowired
	private ResourceRepository resourceRepository;

	@Override
	public boolean forgotPasswordOtp(String email) {
		
		Optional<User> existingUser = userRepository.findByUsername(email);
		
		if(existingUser.isPresent()) {
			User user = existingUser.get();
			String otp = otpUtil.generateOtp();
			
			if(user.isActive()) {
				
				user.setOtp(otp);
				user.setOtpGeneratedTime(LocalDateTime.now());
				
				Thread threadA = new Thread(() -> {
					
					emailService.sendEmail(email, "Disaster Management System",
							"Otp to set the password is as below. Please verify use otp within 5 minutes    \n otp :-  "+otp);
				});
				
				threadA.start();
				
				userRepository.save(user);
				return true;
			}
			else {
				return false;
			}
		}
		
		Optional<Resource> existingResource = resourceRepository.findByUsername(email);
		
		if(existingResource.isPresent()) {
			Resource resource = existingResource.get();
			String otp = otpUtil.generateOtp();
			
			if(resource.isActive()) {
				
				resource.setOtp(otp);
				resource.setOtpGeneratedTime(LocalDateTime.now());
				
				Thread threadA = new Thread(() -> {
					
					emailService.sendEmail(email, "Disaster Management System",
							"Otp to set the password is as below. Please verify use otp within 5 minutes    \n otp :-  "+otp);
				});
				
				threadA.start();
				
				resourceRepository.save(resource);
				return true;
			}
			else {
				return false;
			}
		}
		
		Optional<Admin> existringAdmin = adminRepository.findByUsername(email);
		
		if(existringAdmin.isPresent()) {
			Admin admin = existringAdmin.get();
			String otp = otpUtil.generateOtp();
			
			if(admin.isActive()) {
				
				admin.setOtp(otp);
				admin.setOtpGeneratedTime(LocalDateTime.now());
				
				Thread threadA = new Thread(() -> {
					
					emailService.sendEmail(email, "Disaster Management System",
							"Otp to set the password is as below. Please verify use otp within 5 minutes    \n otp :-  "+otp);
				});
				
				threadA.start();
				
				adminRepository.save(admin);
				return true;
			}
			else {
				return false;
			}
		}
		
		return false;
	}

	@Override
	public boolean setNewPassword(SetNewPasswordDto dto) {

		Optional<User> existingUser = userRepository.findByUsername(dto.getEmail());
		
		if(existingUser.isPresent()) {
			
			User user = existingUser.get();
			if(user.getOtp().equals(dto.getOtp()) && Duration.between(user.getOtpGeneratedTime(), LocalDateTime.now()).getSeconds()<5*60) {
				
				String hashedPassword = passwordEncoder.encode(dto.getNewPassword());
				
				user.setPassword(hashedPassword);
				userRepository.save(user);
				
				Thread threadA = new Thread(() -> {
					
					emailService.sendEmail(dto.getEmail(), "Disaster Management System",
							"Password changed successfully!! \n Login with your new Password");
				});
				
				threadA.start();
				return true;
			}
			else {
				return false;
			}
		}
		
		Optional<Resource> existingResource = resourceRepository.findByUsername(dto.getEmail());
		
		if(existingResource.isPresent()) {
			
			Resource resource = existingResource.get();
			if(resource.getOtp().equals(dto.getOtp()) && Duration.between(resource.getOtpGeneratedTime(), LocalDateTime.now()).getSeconds()<5*60) {
				
				String hashedPassword = passwordEncoder.encode(dto.getNewPassword());
				
				resource.setPassword(hashedPassword);
				resourceRepository.save(resource);
				
				Thread threadA = new Thread(() -> {
					
					emailService.sendEmail(dto.getEmail(), "Disaster Management System",
							"Password changed successfully!! \n Login with your new Password");
				});
				
				threadA.start();
				return true;
			}
			else {
				return false;
			}
		}
		
		Optional<Admin> existringAdmin = adminRepository.findByUsername(dto.getEmail());
		
		if(existringAdmin.isPresent()) {
			
			Admin admin = existringAdmin.get();
			if(admin.getOtp().equals(dto.getOtp()) && Duration.between(admin.getOtpGeneratedTime(), LocalDateTime.now()).getSeconds()<5*60) {
				
				String hashedPassword = passwordEncoder.encode(dto.getNewPassword());
				
				admin.setPassword(hashedPassword);
				adminRepository.save(admin);
				
				Thread threadA = new Thread(() -> {
					
					emailService.sendEmail(dto.getEmail(), "Disaster Management System",
							"Password changed successfully!! \n Login with your new Password");
				});
				
				threadA.start();
				return true;
			}
			else {
				return false;
			}
		}
		
		return false;
	}

	@Override
	public boolean verifyAccout(String email, String otp) {
		
		Optional<User> existingUser = userRepository.findByUsername(email);
		
		if(existingUser.isPresent()) {
			
			User user = existingUser.get();
			
			if(user.getOtp().equals(otp) && Duration.between(user.getOtpGeneratedTime(), LocalDateTime.now()).getSeconds()<5*60) {
				user.setActive(true);
				userRepository.save(user);
				return true;
			}
			else {
				return false;
			}
		}
		
		Optional<Resource> existringResource = resourceRepository.findByUsername(email);
		
		if(existringResource.isPresent()) {
			
			Resource resource = existringResource.get();
			
			if(resource.getOtp().equals(otp) && Duration.between(resource.getOtpGeneratedTime(), LocalDateTime.now()).getSeconds()<5*60) {
				resource.setActive(true);
				resourceRepository.save(resource);
				return true;
			}
			else {
				return false;
			}
		}
		
		return false;
	}

	@Override
	public boolean reGenerateOtp(String email) {
		
		Optional<User> existingUser = userRepository.findByUsername(email);
		
		if(existingUser.isPresent()) {
			
			User user = existingUser.get();
			
			if(!user.isActive()) {
				
				String otp = otpUtil.generateOtp();
				user.setOtp(otp);
				user.setOtpGeneratedTime(LocalDateTime.now());
				
				userRepository.save(user);
				
				Thread threadA = new Thread(() -> {
					
					emailService.sendEmail(email, "Disaster Management System",
							"Please verify otp within 5 minutes \n otp:-   "+otp);
				});
				
				threadA.start();
				return true;
			}
			else {
				return false;
			}
			
			
		}
		
		Optional<Resource> existringResource = resourceRepository.findByUsername(email);
		
		if(existringResource.isPresent()) {
			
			Resource resource = existringResource.get();
			
			if(!resource.isActive()) {
				
				String otp = otpUtil.generateOtp();
				resource.setOtp(otp);
				resource.setOtpGeneratedTime(LocalDateTime.now());
				
				resourceRepository.save(resource);
				
				Thread threadA = new Thread(() -> {
					
					emailService.sendEmail(email, "Disaster Management System",
							"Please verify otp within 5 minutes \n otp:-   "+otp);
				});
				
				threadA.start();
				return true;
			}
			else {
				return false;
			}
		}
		
		return false;
	}

}
