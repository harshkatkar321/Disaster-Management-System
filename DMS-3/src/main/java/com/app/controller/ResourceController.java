package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.entity.Resource;
import com.app.enums.ResourceKind;
import com.app.repository.ResourceRepository;
import com.app.service.ResourceService;

@RestController
@RequestMapping("/api")
public class ResourceController {
	
	@Autowired
	private ResourceService resourceService;
	
	@PutMapping("/resources/{id}")
	public ResponseEntity<?> validateResource(@PathVariable String id){
		try {
			resourceService.validateResource(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/resources/city/{city}")
	public ResponseEntity<?> getResources(@PathVariable String city){
		try {
			List<Resource> byCity = resourceService.findByCity(city);
			return new ResponseEntity<>(byCity, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/resources/notverified/city/{city}")
	public ResponseEntity<?> getNotVerifiedResources(@PathVariable String city){
		try {
			List<Resource> byCity = resourceService.findByCityNotVerified(city);
			return new ResponseEntity<>(byCity, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@GetMapping("/resources/{kind}")
    public ResponseEntity<?> getResourcesByKind(@PathVariable ResourceKind kind) {
	 try {
			List<Resource> byKind = resourceService.findByKind(kind);
			return new ResponseEntity<>(byKind, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
    }

}
