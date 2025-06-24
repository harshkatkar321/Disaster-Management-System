package com.app.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dto.DisasterDto;
import com.app.entity.Disaster;
import com.app.entity.User;
import com.app.repository.DisasterRepository;
import com.app.repository.UserRepository;
import com.app.service.DisasterService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class DisasterServiceImpl implements DisasterService {
	
	@Autowired
	private DisasterRepository disasterRepository;
	@Autowired
	private UserRepository userRepository;

	@Override
	public Disaster createDisaster(DisasterDto disasterDto) {
		// TODO Auto-generated method stub
		User user = userRepository.findById(disasterDto.getUserId()).get();
		
		String id = UUID.randomUUID().toString();
		
		Disaster d = new Disaster(id, disasterDto.getType(), disasterDto.getLocation(), disasterDto.getDescription(),
				disasterDto.getImage(), disasterDto.getStatus(), disasterDto.getDate(), disasterDto.getMapLocation(),
				disasterDto.isActive(), disasterDto.isVerified(), user);
	 
		return disasterRepository.save(d);
	}

	@Override
	public List<Disaster> getAllDisasters() {
		// TODO Auto-generated method stub
		return disasterRepository.findAll();
	}

	@Override
	public List<Disaster> findByLocation(String location) {
		// TODO Auto-generated method stub
		return disasterRepository.findByLocationContainingIgnoreCase(location);
//		return disasterRepository.findByLocation(location);
	}

	@Override
	public boolean changeIsVerified(String id) {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
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
	public Disaster updateDisaster(String id, DisasterDto disasterDto) {
		// TODO Auto-generated method stub
		Disaster existing = disasterRepository.findById(id)
	            .orElseThrow(() -> new EntityNotFoundException("Disaster not found: " + id));

	        existing.setType(disasterDto.getType());
	        existing.setLocation(disasterDto.getLocation());
	        existing.setDescription(disasterDto.getDescription());
	        existing.setImage(disasterDto.getImage());
	        existing.setStatus(disasterDto.getStatus());
	        existing.setDate(disasterDto.getDate());
	        existing.setActive(disasterDto.isActive());
	        existing.setVerified(disasterDto.isVerified());
	       
	        return disasterRepository.saveAndFlush(existing);
	}

	@Override
	public Disaster findById(String id) {
		// TODO Auto-generated method stub
		return disasterRepository.findById(id).get();
	}

	@Override
	public boolean deleteDisaster(String id) {
		// TODO Auto-generated method stub
		Disaster disaster = disasterRepository.findById(id).get();
		disasterRepository.delete(disaster);;
		
		if(disaster != null)
			return true;
		else
			return false;
	}

}
