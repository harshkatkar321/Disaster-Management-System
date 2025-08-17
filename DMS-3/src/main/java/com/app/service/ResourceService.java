package com.app.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.app.dto.ResourceDto;
import com.app.entity.Resource;
import com.app.enums.ResourceKind;

public interface ResourceService {
	
	Resource addResource(ResourceDto dto, MultipartFile imageFile);
	
	void validateResource(String id);
	
	List<Resource> findByCity(String city);
	
	List<Resource> findByCityNotVerified(String city);
	
	List<Resource> findByKind(ResourceKind kind);

}
