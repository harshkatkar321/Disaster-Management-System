package com.app.controller;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.AlertDto;
import com.app.entity.Alert;
import com.app.repository.AlertRepository;
import com.app.service.AlertService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class AlertController {
	
	@Autowired
	private AlertService alertService;
	
	@Autowired
	private AlertRepository alertRepository;
	
	@PostMapping("/alerts")
	public ResponseEntity<?> createAlert(@RequestBody @Valid AlertDto dto, BindingResult br){
		
		if (br.hasErrors()) {
	        var errors = br.getFieldErrors().stream()
	            .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
	            .toList();
	        return ResponseEntity.badRequest().body(Map.of("errors", errors));
	    }
		
		try {
			Alert alert = alertService.createAlert(dto);
			return new ResponseEntity<>(alert, HttpStatus.CREATED);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_GATEWAY);
		}
	}
	
	@GetMapping("/alerts")
	public ResponseEntity<?> getAllAlerts(){
		try {
			List<Alert> allAlerts = alertService.getAllAlerts();
			return new ResponseEntity<>(allAlerts, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NO_CONTENT);
		}
	}
	
	@GetMapping("/alerts/{id}")
	public ResponseEntity<?> getAlert(@PathVariable String id){
		try {
			Optional<Alert> alert = alertService.getAlert(id);
			return new ResponseEntity<>(alert, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NO_CONTENT);
		}
	}
	
	@GetMapping("/alerts/location/{location}")
	public ResponseEntity<?> getAllAlertsByLocation(@PathVariable String location){
		try {
			List<Alert> allAlerts = alertService.getByLocation(location);
			
			  if (allAlerts == null || allAlerts.isEmpty()) {
		            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No alerts found for this location.");
		        }
			
			return new ResponseEntity<>(allAlerts, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NO_CONTENT);
		}
	}
	
	@PutMapping("/alerts/{id}")
	public ResponseEntity<?> updateAlert(@PathVariable String id, @RequestBody @Valid AlertDto dto,  BindingResult br){
		
		if (br.hasErrors()) {
	        var errors = br.getFieldErrors().stream()
	            .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
	            .toList();
	        return ResponseEntity.badRequest().body(Map.of("errors", errors));
	    }
		
		try {
			Alert updateAlert = alertService.updateAlert(id, dto);
			return new ResponseEntity<>(updateAlert, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NO_CONTENT);
		}
	}
	
	@GetMapping("/alerts/message/{id}")
	public ResponseEntity<?> getMessage(@PathVariable String id){
		try {
			String message = alertService.getMessage(id);
			return new ResponseEntity<>(message, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NO_CONTENT);
		}
	}
	
	@GetMapping("/alerts/guideline/{id}")
	public ResponseEntity<?> getGuidelines(@PathVariable String id){
		try {
			List<String> guidelines = alertService.getGuidelines(id);
			return new ResponseEntity<>(guidelines, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NO_CONTENT);
		}
	}
	
	@DeleteMapping("/alerts/{id}")
	public ResponseEntity<?> deleteAlert(@PathVariable String id) {
	    try {
	    	
	        alertRepository.deleteById(id);
	        return ResponseEntity.ok(Map.of("message", "Alert deleted successfully"));
	    } catch (NoSuchElementException e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                             .body(Map.of("error", "Alert not found with ID: " + id));
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                             .body(Map.of("error", "Error deleting alert: " + e.getMessage()));
	    }
	}


}
