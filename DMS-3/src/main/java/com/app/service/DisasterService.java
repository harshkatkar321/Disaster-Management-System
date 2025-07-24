package com.app.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.app.dto.DisasterDto;
import com.app.entity.Disaster;

public interface DisasterService {
	
	List<Disaster> getAllDisasters();
	
	List<Disaster> findByLocation(String location);
	
	boolean changeIsVerified(String id);
	
	boolean changeIsActive(String id);
	
	List<Disaster> findByIsVerifiedFalseAndLocation(String location);
	
	Disaster updateDisaster(String id, DisasterDto disasterDto, MultipartFile imageFile);
	
	Disaster findById(String id);
	
	boolean deleteDisaster(String id);

	Disaster addDisaster(DisasterDto dto, MultipartFile imageFile);

}
