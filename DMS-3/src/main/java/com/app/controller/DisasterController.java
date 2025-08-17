package com.app.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.app.dto.DisasterDto;
import com.app.entity.Disaster;
import com.app.service.DisasterService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class DisasterController {
	
	@Autowired
	private DisasterService disasterService;
	
	@PostMapping("/disasters")
	public ResponseEntity<?> createDisaster(@RequestPart @Valid DisasterDto dto,   BindingResult br,
			@RequestPart(required = false) MultipartFile imageFile){
		
		if (br.hasErrors()) {
	        var errors = br.getFieldErrors().stream()
	            .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
	            .toList();
	        return ResponseEntity.badRequest().body(Map.of("errors", errors));
	    }
		
		try {
		 Disaster disaster =	disasterService.addDisaster(dto, imageFile);
		 return new ResponseEntity<>(disaster, HttpStatus.CREATED);
			
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/disasters")
	public ResponseEntity<?> getAllDisaters(){
		try {
			List<DisasterDto> allDisasters = disasterService.getAllDisasters();
			return new ResponseEntity<>(allDisasters, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NO_CONTENT);
		}
	}
	
	@GetMapping("/disasters/location/{location}")
	public ResponseEntity<?> findByLocation(@PathVariable String location){
		try {
			List<Disaster> allDisasters = disasterService.findByLocation(location);
			return new ResponseEntity<>(allDisasters, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NO_CONTENT);
		}
	}
	
	@GetMapping("/disasters/verify/{id}")
	public ResponseEntity<?> changeisVerified(@PathVariable String id) {
		try {
			boolean changeIsVerified = disasterService.changeIsVerified(id);
			return new ResponseEntity<>(changeIsVerified, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NO_CONTENT);
		}
	}
	
	//this will change the isActive status if true then false and wise versa
		@GetMapping("/disasters/active/{id}")
		public ResponseEntity<?> changeIsActive(@PathVariable String id) {
			try {
				boolean changeIsActive = disasterService.changeIsActive(id);
				return new ResponseEntity<>(changeIsActive, HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<>(e.getMessage(), HttpStatus.NO_CONTENT);
			}
		}
		
		@GetMapping("/disasters/notverifiedbylocation/{location}")
		public ResponseEntity<?>  findByIsVerifiedFalseAndLocation(@PathVariable String location){
			
			try {
				List<Disaster> byIsVerifiedFalseAndLocation = disasterService.findByIsVerifiedFalseAndLocation(location);
				return new ResponseEntity<>(byIsVerifiedFalseAndLocation, HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<>(e.getMessage(), HttpStatus.NO_CONTENT);
			}
		}
		
		@PutMapping("/disasters/{id}")
		public ResponseEntity<?> updateDisaster(@PathVariable String id, @RequestPart @Valid DisasterDto disasterDto,
				BindingResult br,
				@RequestPart(required = false) MultipartFile imageFile) {
			
			if (br.hasErrors()) {
		        var errors = br.getFieldErrors().stream()
		            .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
		            .toList();
		        return ResponseEntity.badRequest().body(Map.of("errors", errors));
		    }
			
			try {
				Disaster updateDisaster = disasterService.updateDisaster(id, disasterDto, imageFile);
				return new ResponseEntity<>(updateDisaster, HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<>(e.getMessage(), HttpStatus.NO_CONTENT);
			}
		}
		
		@GetMapping("/disasters/{id}")
		public ResponseEntity<?> findById(@PathVariable String id) {
			try {
				Disaster disaster = disasterService.findById(id);
				return new ResponseEntity<>(disaster, HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<>(e.getMessage(), HttpStatus.NO_CONTENT);
			}
		}
		
		@DeleteMapping("/disasters/{id}")
		public ResponseEntity<?> deleteDisaster(@PathVariable String id) {
			
			try {
				boolean deleteDisaster = disasterService.deleteDisaster(id);
				return new ResponseEntity<>(deleteDisaster, HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<>(e.getMessage(), HttpStatus.NO_CONTENT);
			}
		}

}
