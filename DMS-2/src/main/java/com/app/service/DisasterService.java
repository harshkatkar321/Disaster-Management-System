package com.app.service;

import java.util.List;

import com.app.dto.DisasterDto;
import com.app.entity.Disaster;

public interface DisasterService {
	
	Disaster createDisaster(DisasterDto disasterDto);
	
	List<Disaster> getAllDisasters();
	
	List<Disaster> findByLocation(String location);
	
	boolean changeIsVerified(String id);
	
	boolean changeIsActive(String id);
	
	List<Disaster> findByIsVerifiedFalseAndLocation(String location);
	
	Disaster updateDisaster(String id, DisasterDto disasterDto);
	
	Disaster findById(String id);
	
	boolean deleteDisaster(String id);

}
