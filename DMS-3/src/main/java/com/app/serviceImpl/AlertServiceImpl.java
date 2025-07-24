package com.app.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dto.AlertDto;
import com.app.entity.Admin;
import com.app.entity.Alert;
import com.app.entity.Disaster;
import com.app.entity.User;
import com.app.repository.AdminRepository;
import com.app.repository.AlertRepository;
import com.app.repository.DisasterRepository;
import com.app.repository.UserRepository;
import com.app.service.AlertService;

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
		
//		alert.setUserId(dto.getUserId());
//		alert.setAdminId(dto.getAdminId());
		
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
		
		return alertRepository.save(alert);
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

	@Override
	public Alert updateAlert(String id, AlertDto dto) {
		// TODO Auto-generated method stub
		Optional<Alert> existingAlert = alertRepository.findById(id);
		
		if(!existingAlert.isPresent()) {
			return null;
		}
		
		Alert alert = existingAlert.get();
		
		alert.setType(dto.getType());
		alert.setLocation(dto.getLocation());
		alert.setDescription(dto.getDescription());
		alert.setSeverity(dto.getSeverity());
		alert.setRegion(dto.getRegion());
		alert.setRiskScore(dto.getRiskScore());
		alert.setMessage(dto.getMessage());
		
		alert.setTags(dto.getTags());
		
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
