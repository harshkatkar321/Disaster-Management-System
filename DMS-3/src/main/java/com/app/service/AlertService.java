package com.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.app.dto.AlertDto;
import com.app.dto.AlertWithoutUserDto;
import com.app.entity.Alert;

public interface AlertService {
	
	Alert createAlert(AlertDto dto);
	
	Alert createAlertWithoutuser(AlertWithoutUserDto dto, MultipartFile imageFile);
	
	List<Alert> getAllAlerts();
	
	Optional<Alert> getAlert(String id);
	
	List<Alert> getByLocation(String location);
	
	Alert updateAlert(String id, AlertDto dto);
	
	String getMessage(String id);
	
	List<String> getGuidelines(String id);

}
