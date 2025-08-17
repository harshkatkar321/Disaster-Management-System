package com.app.serviceImpl;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.app.dto.ChangePasswordDto;
import com.app.dto.MailDto;
import com.app.dto.RegisterUserDto;
import com.app.dto.SetNewPasswordDto;
import com.app.dto.SmsDto;
import com.app.dto.UpdatUserDto;
import com.app.entity.Admin;
import com.app.entity.SuperAdmin;
import com.app.entity.User;
import com.app.enums.Role;
import com.app.exception.UsernameAlreadyExistsException;
import com.app.repository.AdminRepository;
import com.app.repository.SuperAdminRepository;
import com.app.repository.UserRepository;
import com.app.service.EmailService;
import com.app.service.MyUserService;
import com.app.service.SmsService;
import com.app.util.OtpUtil;

import jakarta.persistence.EntityNotFoundException;

@Service
public class MyUserServiceImpl implements MyUserService {
	
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


	@Override
	public User addUser(RegisterUserDto dto, MultipartFile imageFile) {
		// TODO Auto-generated method stub
		
		Optional<SuperAdmin> existring2 = superAdminRepository.findByUsername(dto.getEmail());
		
		Optional<Admin> existing1 = adminRepository.findByUsername(dto.getEmail());
		
		Optional<User> existing = userRepository.findByUsername(dto.getEmail());
		if (existing.isPresent() || existing1.isPresent() || existring2.isPresent()) {
		    throw new UsernameAlreadyExistsException(dto.getEmail());
		}
		
		String hashedPassword = passwordEncoder.encode(dto.getPassword());
		String userId = UUID.randomUUID().toString();
		
		User user = new User();
		user.setId(userId);
		user.setFirstName(dto.getFirstName());
		user.setLastName(dto.getLastName());
		user.setUsername(dto.getEmail());
		user.setPassword(hashedPassword);
		user.setCity(dto.getCity());
		user.setPhoneNumber(dto.getPhoneNumber());
		user.setRole(Role.USER);
		
		 if (imageFile != null && !imageFile.isEmpty()) {
			 
		        user.setProfilePicture(imageFile.getOriginalFilename());
		        user.setImageType(imageFile.getContentType());
		        try {
		            user.setImageData(imageFile.getBytes());
		        } catch (IOException e) {
		            throw new RuntimeException("Failed to process image", e);
		        }
		    }
		 
		//otp
			String otp = otpUtil.generateOtp();
			user.setOtp(otp);
			user.setOtpGeneratedTime(LocalDateTime.now());
			
			User save = userRepository.save(user);
			
			Thread threadA = new Thread(() -> {
				
				emailService.sendEmail(dto.getEmail(), "Disaster Management System",
						"Please verify otp within 5 minutes \n otp:-   "+otp);
			});
			
			threadA.start();
		
		return save;
	}


	@Override
	public List<User> getUsers() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}

	@Override
	public User getUser(String username) {
		// TODO Auto-generated method stub
		Optional<User> existing = userRepository.findByUsername(username);
		if (existing.isPresent()) {
			return existing.get();
		}
		else {
			return null;
		}
	}

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
	public void deleteUser(String id) {
		// TODO Auto-generated method stub
		Optional<User> existing = userRepository.findById(id);
		if (existing.isPresent()) {
			userRepository.delete(existing.get());
		}
	}

	@Override
	public List<User> findByLocation(String city) {
		// TODO Auto-generated method stub
		return userRepository.findByCityContainingIgnoreCase(city);
	}

	@Override
	public List<String> findPhoneNumberByCity(String city) {
		// TODO Auto-generated method stub
		return userRepository.findPhoneNumberByCityContainingIgnoreCase(city);
	}

	@Override
	public List<String> findEmailByCity(String city) {
		// TODO Auto-generated method stub
		return userRepository.findUsernameByCityContainingIgnoreCase(city);
	}
	
	@Override
	public void sendMailByCity(String city, MailDto dto) {
		// TODO Auto-generated method stub
		
		List<String> mails = userRepository.findUsernameByCityContainingIgnoreCase(city);
		
		for (String mail : mails) {
			System.out.println(mail);
			emailService.sendEmail(mail,dto.getSubject(), dto.getBody());
		}
	}

	@Override
	public void sendSmsByCity(String city, SmsDto smsDto) {
		
		List<String> phoneNumbers = userRepository.findPhoneNumberByCityContainingIgnoreCase(city);
		
		for(String number : phoneNumbers)
		{
			number = "+91 " + number;
			System.out.println(number);
			
			
			try {
				smsService.sms(smsDto.getMessage(), number);
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(number+" "+e.getMessage());
			}
			
			
		}
		
	}

	@Override
	public boolean changePassword(ChangePasswordDto dto) {
		// TODO Auto-generated method stub
		User user = userRepository.findByUsername(dto.getEmail()).orElseThrow(()->
						new UsernameNotFoundException("User not foud with email : "+dto.getEmail()));
		
		boolean result = passwordEncoder.matches(dto.getOldPassword(), user.getPassword());
		
		if(result) {
			String newHashedPassword = passwordEncoder.encode(dto.getNewPassword());
			user.setPassword(newHashedPassword);
			userRepository.saveAndFlush(user);
			return true;
		}
		else {
			return false;
		}
	}
}
