package com.app.serviceImpl;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.app.dto.ResourceDto;
import com.app.entity.Admin;
import com.app.entity.Resource;
import com.app.entity.SuperAdmin;
import com.app.entity.User;
import com.app.enums.ResourceKind;
import com.app.enums.ResourceStatus;
import com.app.enums.Role;
import com.app.exception.UsernameAlreadyExistsException;
import com.app.repository.AdminRepository;
import com.app.repository.ResourceRepository;
import com.app.repository.SuperAdminRepository;
import com.app.repository.UserRepository;
import com.app.service.EmailService;
import com.app.service.ResourceService;
import com.app.util.OtpUtil;

@Service
public class ResourceServiceImpl implements ResourceService {
	
	@Autowired
	private ResourceRepository resourceRepository;
	
	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private SuperAdminRepository superAdminRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private OtpUtil otpUtil;
	
	@Autowired
	private EmailService emailService;

	@Override
	public Resource addResource(ResourceDto dto, MultipartFile imageFile) {
		
		Optional<Resource> byUsername = resourceRepository.findByUsername(dto.getEmail());
		
		Optional<SuperAdmin> existring2 = superAdminRepository.findByUsername(dto.getEmail());
		
		Optional<Admin> existing1 = adminRepository.findByUsername(dto.getEmail());
		
		Optional<User> existing = userRepository.findByUsername(dto.getEmail());
		if (byUsername.isPresent() || existing.isPresent() || existing1.isPresent() || existring2.isPresent()) {
		    throw new UsernameAlreadyExistsException(dto.getEmail());
		}
		
		String hashedPassword = passwordEncoder.encode(dto.getPassword());
		String id = UUID.randomUUID().toString();
		
		Resource resource = new Resource();
		resource.setId(id);
		resource.setKind(dto.getKind());
		resource.setType(dto.getType());
		resource.setName(dto.getName());
		resource.setPhoneNumber(dto.getPhoneNumber());
		
		resource.setUsername(dto.getEmail());
		resource.setPassword(hashedPassword);
		
		resource.setCapacity(dto.getCapacity());
		resource.setCity(dto.getCity());
		resource.setDescription(dto.getDescription());
		
		resource.setVerified(false);
		resource.setStatus(ResourceStatus.AVAILABLE);
		resource.setRole(Role.RESOURCE);
		
		 if (imageFile != null && !imageFile.isEmpty()) {
			 
			 resource.setImageName(imageFile.getOriginalFilename());
			 resource.setImageType(imageFile.getContentType());
		        try {
		        	resource.setImageData(imageFile.getBytes());
		        } catch (IOException e) {
		            throw new RuntimeException("Failed to process image", e);
		        }
		    }
		 
		 String otp = otpUtil.generateOtp();
		 resource.setOtp(otp);
		 resource.setOtpGeneratedTime(LocalDateTime.now());

		Resource save = resourceRepository.save(resource);
		
		Thread threadA = new Thread(() -> {
			
			emailService.sendEmail(dto.getEmail(), "Disaster Management System",
					"Please verify otp within 5 minutes \n otp:-   "+otp);
		});
		
		threadA.start();
		
		return save;
	}

	@Override
	public void validateResource(String id) {
		// TODO Auto-generated method stub
		
		Optional<Resource> existingResource = resourceRepository.findById(id);
		if(existingResource.isPresent())
		{
			Resource resource = existingResource.get();
			resource.setVerified(true);
			resourceRepository.save(resource);
		}
		
	}

	@Override
	public List<Resource> findByCity(String city) {
		// TODO Auto-generated method stub
		List<Resource> resourcesList = new ArrayList<>();
		
		List<Resource> byCityContainingIgnoreCase = resourceRepository.findByCityContainingIgnoreCase(city);
		for (Resource resource : byCityContainingIgnoreCase) {
			if(resource.isVerified())
				resourcesList.add(resource);
		}
		return resourcesList;
	}

	@Override
	public List<Resource> findByCityNotVerified(String city) {
		List<Resource> resourcesList = new ArrayList<>();
		
		List<Resource> byCityContainingIgnoreCase = resourceRepository.findByCityContainingIgnoreCase(city);
		for (Resource resource : byCityContainingIgnoreCase) {
			if(!resource.isVerified())
				resourcesList.add(resource);
		}
		return resourcesList;
	}
	
	@Override
	public List<Resource> findByKind(ResourceKind kind) {
	    List<Resource> resourcesList = new ArrayList<>();
	    
	    // Fetch resources by kind from repository
	    List<Resource> byKind = resourceRepository.findByKind(kind);
	    
	    // If you need any additional filtering, you can do it here
	    for (Resource resource : byKind) {
	    	if(resource.isVerified())
				resourcesList.add(resource);
	        
	    }
	    
	    return resourcesList;
	}

}
