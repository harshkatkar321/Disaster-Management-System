package com.app.serviceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.app.dto.AlertDto;
import com.app.dto.AlertWithoutUserDto;
import com.app.entity.Admin;
import com.app.entity.Alert;
import com.app.entity.Disaster;
import com.app.entity.Resource;
import com.app.entity.User;
import com.app.enums.ResourceStatus;
import com.app.repository.AdminRepository;
import com.app.repository.AlertRepository;
import com.app.repository.DisasterRepository;
import com.app.repository.ResourceRepository;
import com.app.repository.UserRepository;
import com.app.service.AlertService;
import com.app.service.EmailService;
import com.app.service.SmsService;

@Service
public class AlertServiceImpl implements AlertService {
	
	@Autowired
	private AlertRepository alertRepository;
	
	@Autowired
	private DisasterRepository disasterRepository;
	
	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ResourceRepository resourceRepository;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private SmsService smsService;

	@Override
	public Alert createAlert(AlertDto dto) {
		
		Alert alert = new Alert();
		alert.setId(UUID.randomUUID().toString());
		alert.setType(dto.getType());
		alert.setLocation(dto.getLocation());
		alert.setDescription(dto.getDescription());
		alert.setSeverity(dto.getSeverity());
		alert.setRegion(dto.getRegion());
		alert.setRiskScore(dto.getRiskScore());
		alert.setMessage(dto.getMessage());
		
		alert.setTags(dto.getTags());
		
		ArrayList<Resource> list = new ArrayList<>();
		for (String resourceId : dto.getResourceIds()) {
	
			Optional<Resource> resource = resourceRepository.findById(resourceId);
			
			list.add(resource.get());
		}
		alert.setResources(list);
		
		Optional<Disaster> existingDisaster = disasterRepository.findById(dto.getDisasterId());
		if(existingDisaster.isPresent()) {
			alert.setDisaster(existingDisaster.get());
		}
		
		Optional<Admin> existingAdmin = adminRepository.findById(dto.getAdminId());
		if(existingAdmin.isPresent()) {
			alert.setAdmin(existingAdmin.get());
		}
		
		Optional<User> existingUser = userRepository.findById(dto.getUserId());
		if(existingUser.isPresent()) {
			alert.setUser(existingUser.get());
		}
		
		
		Alert save = alertRepository.save(alert);
		
		//email
		List<String> userMails = userRepository.findUsernameByCityContainingIgnoreCase(dto.getLocation());
		
		Thread threadA = new Thread(() -> {
			
			for (String mail : userMails) {
				
				emailService.sendEmail(mail, dto.getType(), dto.getMessage());
			}
		});
		
		threadA.start();
		
		//SMS
		List<String> phoneNumbers = userRepository.findPhoneNumberByCityContainingIgnoreCase(dto.getLocation());
		
		Thread threadB = new Thread(() -> {
			
			for(String number : phoneNumbers)
			{
				number = "+91 " + number;
				System.out.println(number);
				
				try {
					smsService.sms(dto.getMessage(), number);
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println(number+" "+e.getMessage());
				}
			}
		});
		
		threadB.start();
				
		return save;
	}
	
	@Override
	public Alert createAlertWithoutuser(AlertWithoutUserDto dto, MultipartFile imageFile) {
		// TODO Auto-generated method stub
		Alert alert = new Alert();
		alert.setId(UUID.randomUUID().toString());
		alert.setType(dto.getType());
		alert.setLocation(dto.getLocation());
		alert.setDescription(dto.getDescription());
		alert.setSeverity(dto.getSeverity());
		alert.setRegion(dto.getRegion());
		alert.setRiskScore(dto.getRiskScore());
		alert.setMessage(dto.getMessage());
		
		alert.setTags(dto.getTags());
		
		ArrayList<Resource> list = new ArrayList<>();
		for (String resourceId : dto.getResourceIds()) {
	
			Optional<Resource> resource = resourceRepository.findById(resourceId);
			list.add(resource.get());
		}
		alert.setResources(list);
		
		Optional<Admin> existingAdmin = adminRepository.findById(dto.getAdminId());
		if(existingAdmin.isPresent()) {
			alert.setAdmin(existingAdmin.get());
		}
		
		Disaster disaster = new Disaster();
		
		
//		alert.setDisaster(disaster);
		
		if (imageFile != null && !imageFile.isEmpty()) {
			
			alert.setImageName(imageFile.getOriginalFilename());
			alert.setImageType(imageFile.getContentType());
			try {
				alert.setImageData(imageFile.getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				throw new RuntimeException("Failed to process image", e);
			}
		}
		
		Alert save = alertRepository.save(alert);
		
		//Email
		List<String> userMails = userRepository.findUsernameByCityContainingIgnoreCase(dto.getLocation());
		
		Thread threadA = new Thread(() -> {
			
			for (String mail : userMails) {
				
				emailService.sendEmail(mail, dto.getType(), dto.getMessage());
			}
		});
		
		threadA.start();
		
		//SMS
		List<String> phoneNumbers = userRepository.findPhoneNumberByCityContainingIgnoreCase(dto.getLocation());
		
		Thread threadB = new Thread(() -> {
			
			for(String number : phoneNumbers)
			{
				number = "+91 " + number;
				System.out.println(number);
				
				try {
					smsService.sms(dto.getMessage(), number);
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println(number+" "+e.getMessage());
				}
			}
		});
		
		threadB.start();
		
		return save;
	}

	@Override
	public List<Alert> getAllAlerts() {
		// TODO Auto-generated method stub
		return alertRepository.findAll();
	}

	@Override
	public Optional<Alert> getAlert(String id) {
		// TODO Auto-generated method stub
		return alertRepository.findById(id);
	}

	@Override
	public List<Alert> getByLocation(String location) {
		// TODO Auto-generated method stub
		return alertRepository.findByLocationContainingIgnoreCase(location);
	}

//	@Override
//	public Alert updateAlert(String id, AlertDto dto) {
//		// TODO Auto-generated method stub
//		Optional<Alert> existingAlert = alertRepository.findById(id);
//		
//		if(!existingAlert.isPresent()) {
//			return null;
//		}
//		
//		Alert alert = existingAlert.get();
//		
//		alert.setType(dto.getType());
//		alert.setLocation(dto.getLocation());
//		alert.setDescription(dto.getDescription());
//		alert.setSeverity(dto.getSeverity());
//		alert.setRegion(dto.getRegion());
//		alert.setRiskScore(dto.getRiskScore());
//		alert.setMessage(dto.getMessage());
//		
//		alert.setTags(dto.getTags());
//		
//		Optional<Disaster> existingDisaster = disasterRepository.findById(dto.getDisasterId());
//		if(existingDisaster.isPresent()) {
//			alert.setDisaster(existingDisaster.get());
//		}
//		
//		Optional<Admin> existingAdmin = adminRepository.findById(dto.getAdminId());
//		if(existingAdmin.isPresent()) {
//			alert.setAdmin(existingAdmin.get());
//		}
//		
//		Optional<User> existingUser = userRepository.findById(dto.getUserId());
//		if(existingUser.isPresent()) {
//			alert.setUser(existingUser.get());
//		}
//		
//		return alertRepository.saveAndFlush(alert);
//	}
	
	@Override
	public Alert updateAlert(String id, AlertDto dto) {
	    Optional<Alert> existingAlertOpt = alertRepository.findById(id);

	    if (!existingAlertOpt.isPresent()) {
	        return null;
	    }

	    Alert alert = existingAlertOpt.get();

	    // Update basic fields
	    alert.setType(dto.getType());
	    alert.setLocation(dto.getLocation());
	    alert.setDescription(dto.getDescription());
	    alert.setSeverity(dto.getSeverity());
	    alert.setRegion(dto.getRegion());
	    alert.setRiskScore(dto.getRiskScore());
	    alert.setMessage(dto.getMessage());
	    alert.setTags(dto.getTags());

	    // Update linked disaster, admin, user
	    disasterRepository.findById(dto.getDisasterId())
	        .ifPresent(alert::setDisaster);
	    adminRepository.findById(dto.getAdminId())
	        .ifPresent(alert::setAdmin);
	    userRepository.findById(dto.getUserId())
	        .ifPresent(alert::setUser);

	    // === RESOURCE UPDATION LOGIC ===
	    List<String> newResourceIds = dto.getResourceIds() != null ? dto.getResourceIds() : new ArrayList<>();

	    // Current resources linked to this alert
	    List<Resource> currentResources = alert.getResources();

	    // Remove resources that are not in the new list
	    List<Resource> resourcesToRemove = currentResources.stream()
	        .filter(r -> !newResourceIds.contains(r.getId()))
	        .toList();
	    for (Resource res : resourcesToRemove) {
	        res.setStatus(ResourceStatus.AVAILABLE); // or whatever your default is
	        res.setAlerts(null);
	        resourceRepository.save(res);
	    }
	    
	    List<Alert> alerts = new ArrayList<Alert>();
	    alerts.add(alert);
	    // Assign new resources that are not already linked
	    List<String> currentResourceIds = currentResources.stream()
	        .map(Resource::getId)
	        .toList();
	    for (String resourceId : newResourceIds) {
	        if (!currentResourceIds.contains(resourceId)) {
	            resourceRepository.findById(resourceId).ifPresent(res -> {
	                res.setStatus(ResourceStatus.ASSIGNED);
	                res.setAlerts(alerts);
	                resourceRepository.save(res);
	                currentResources.add(res);
	            });
	        }
	    }

	    // Make sure alert's resource list is updated
	    alert.setResources(currentResources);

	    return alertRepository.saveAndFlush(alert);
	}


	@Override
	public String getMessage(String id) {
		// TODO Auto-generated method stub
		Optional<Alert> existingAlert = alertRepository.findById(id);
		
		if(existingAlert.isPresent()) {
			return existingAlert.get().getMessage();
		}
		return null;
	}

	@Override
	public List<String> getGuidelines(String id) {
		// TODO Auto-generated method stub
		Optional<Alert> alert = alertRepository.findById(id);
		
		if(alert.isPresent()) {
			return alert.get().getTags();
		}
		
		return null;
	}

	

}
