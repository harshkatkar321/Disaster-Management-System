package com.app.service;

import java.util.List;
import java.util.Optional;

import com.app.dto.AlertDto;
import com.app.entity.Alert;

public interface AlertService {
	
	Alert createAlert(AlertDto dto);
	
	List<Alert> getAllAlerts();
	
	Optional<Alert> getAlert(String id);
	
	List<Alert> getByLocation(String location);
	
	Alert updateAlert(String id, AlertDto dto);
	
	String getMessage(String id);
	
	List<String> getGuidelines(String id);

}
