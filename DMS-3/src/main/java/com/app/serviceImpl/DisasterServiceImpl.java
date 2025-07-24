package com.app.serviceImpl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.app.dto.DisasterDto;
import com.app.entity.Disaster;
import com.app.entity.User;
import com.app.repository.DisasterRepository;
import com.app.repository.UserRepository;
import com.app.service.DisasterService;
import com.app.service.MyUserService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Lob;

@Service
public class DisasterServiceImpl implements DisasterService {
	
	@Autowired
	private DisasterRepository disasterRepository;
	
	
	@Autowired
	private UserRepository userRepository;


	@Override
	public List<Disaster> getAllDisasters() {
		// TODO Auto-generated method stub
		return disasterRepository.findAll();
	}

	@Override
	public List<Disaster> findByLocation(String location) {
		// TODO Auto-generated method stub
		return disasterRepository.findByLocationContainingIgnoreCase(location);
	}

	@Override
	public boolean changeIsVerified(String id) {
		Disaster disaster = disasterRepository.findById(id).get();
		
		boolean status = disaster.isVerified();
		if(status) {
			disaster.setVerified(false);
		}else {
			disaster.setVerified(true);
		}
		
		 disasterRepository.save(disaster);
		return true;
	}

	@Override
	public boolean changeIsActive(String id) {
		Disaster disaster = disasterRepository.findById(id).get();
		
		boolean status = disaster.isActive();
		if(status) {
			disaster.setActive(false);
		}else {
			disaster.setActive(true);
		}
		
		 disasterRepository.save(disaster);
		
		return true;
	}

	@Override
	public List<Disaster> findByIsVerifiedFalseAndLocation(String location) {
		// TODO Auto-generated method stub
		return disasterRepository.findByIsVerifiedFalseAndLocationContainingIgnoreCase(location);
	}

	@Override
	public Disaster updateDisaster(String id, DisasterDto disasterDto, MultipartFile imageFile) {
		
		Optional<Disaster> existing = disasterRepository.findById(id);
		
		if(!existing.isPresent()) {
			throw new EntityNotFoundException("Disaster not found: " + id);
		}
			
		Disaster disaster = existing.get();
		disaster.setDescription(disasterDto.getDescription());
		disaster.setStatus(disasterDto.getStatus());
		
		//image
				if (imageFile != null && !imageFile.isEmpty()) {
					
					disaster.setImageName(imageFile.getOriginalFilename());
					disaster.setImageType(imageFile.getContentType());
					try {
						disaster.setImageData(imageFile.getBytes());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						throw new RuntimeException("Failed to process image", e);
					}
				}
	        
	    return disasterRepository.saveAndFlush(disaster);
	}

	@Override
	public Disaster findById(String id) {
		// TODO Auto-generated method stub
		return disasterRepository.findById(id).get();
	}

	@Override
	public boolean deleteDisaster(String id) {
		
		Optional<Disaster> existing = disasterRepository.findById(id);
		
		if(existing.isPresent()) {
			disasterRepository.delete(existing.get());
			return true;
		}
		else {
			return false;
		}
	}
	

	@Override
	public Disaster addDisaster(DisasterDto dto, MultipartFile imageFile) {
		
		User user = userRepository.findByUsername(dto.getUsername()).get();
		
		Disaster disaster = new Disaster();
		disaster.setId(UUID.randomUUID().toString());
		disaster.setType(dto.getType());
		disaster.setDescription(dto.getDescription());
		disaster.setLocation(dto.getLocation());
		disaster.setStatus(dto.getStatus());
		disaster.setUser(user);
		
		//image
		if (imageFile != null && !imageFile.isEmpty()) {
			
			disaster.setImageName(imageFile.getOriginalFilename());
			disaster.setImageType(imageFile.getContentType());
			try {
				disaster.setImageData(imageFile.getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				throw new RuntimeException("Failed to process image", e);
			}
		}
		
		//location
		var gf = new org.locationtech.jts.geom.GeometryFactory(new org.locationtech.jts.geom.PrecisionModel(), 4326);
	    var point = gf.createPoint(new org.locationtech.jts.geom.Coordinate(dto.getLng(), dto.getLat()));
	    disaster.setMapLocation(point);
		
		return disasterRepository.save(disaster);
	}

}
