package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.DisasterDto;
import com.app.entity.Disaster;
import com.app.service.DisasterService;

@RestController
public class DisasterController {
	
	@Autowired
	private DisasterService disasterService;
	
	@PostMapping("/disasters")
	public Disaster addDisster(@RequestBody DisasterDto disasterDto) {
		return disasterService.createDisaster(disasterDto);
	}
	
	@GetMapping("/disasters")
	public List<Disaster> getAllDisaters(){
		return disasterService.getAllDisasters();
	}
	
	@GetMapping("/disasters/location/{location}")
	public List<Disaster> findByLocation(@PathVariable String location){
		return disasterService.findByLocation(location);
	}
	
	@GetMapping("/disasters/verify/{id}")
	public boolean changeisVerified(@PathVariable String id) {
		return disasterService.changeIsVerified(id);
	}
	
	//this will change the isActive status if true then false and wise versa
	@GetMapping("/disasters/active/{id}")
	public boolean changeIsActive(@PathVariable String id) {
		return disasterService.changeIsActive(id);
	}
	
	@GetMapping("/disasters/notverifiedbylocation/{location}")
	public List<Disaster> findByIsVerifiedFalseAndLocation(@PathVariable String location){
		
		return disasterService.findByIsVerifiedFalseAndLocation(location);	
	}
	
	@PutMapping("/disasters/{id}")
	public Disaster updateDisaster(@PathVariable String id, @RequestBody DisasterDto disasterDto) {
		return disasterService.updateDisaster(id, disasterDto);
	}
	
	@GetMapping("/disasters/{id}")
	public Disaster findById(@PathVariable String id) {
		return disasterService.findById(id);
	}
	
	@DeleteMapping("/disasters/{id}")
	public boolean deleteDisaster(@PathVariable String id) {
		return disasterService.deleteDisaster(id);
	}

}
