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

import com.app.dto.RegisterUserDto;
import com.app.dto.UpdatUserDto;
import com.app.entity.Admin;
import com.app.entity.User;
import com.app.service.AdminService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	
	@PostMapping("/admin")
	public ResponseEntity<?> addAdmin(@RequestPart @Valid RegisterUserDto dto, BindingResult br,
			@RequestPart(required = false) MultipartFile imageFile){
		
		if (br.hasErrors()) {
	        var errors = br.getFieldErrors().stream()
	            .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
	            .toList();
	        return ResponseEntity.badRequest().body(Map.of("errors", errors));
	    }
		
		try {
			Admin admin = adminService.addUser(dto, imageFile);
			return new ResponseEntity<>(admin, HttpStatus.CREATED);
			
		} catch (Exception e) {
			
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/admin")
	public ResponseEntity<?> getAllAdmin(){
		try {
			List<Admin> allAdmin = adminService.getAllAdmin();
			return new ResponseEntity<>(allAdmin, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/admin/{id}")
	public ResponseEntity<?> getAdmin(@PathVariable String id) {
		try {
			Admin admin = adminService.getAdmin(id);
			return new ResponseEntity<>(admin, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/admin/{id}")
	public ResponseEntity<?> deleteAdmin(@PathVariable String id) {
		try {
			boolean deleteAdmin = adminService.deleteAdmin(id);
			return new ResponseEntity<>(deleteAdmin, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/admin/{id}")
	public ResponseEntity<?> updateAdmin(@PathVariable String id, @RequestPart @Valid UpdatUserDto userUpdateDto,
			BindingResult br,
			@RequestPart(required = false) MultipartFile imageFile) {
		
		if (br.hasErrors()) {
	        var errors = br.getFieldErrors().stream()
	            .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
	            .toList();
	        return ResponseEntity.badRequest().body(Map.of("errors", errors));
	    }
		
		try {
			Admin updateAdmin = adminService.updateAdmin(id, userUpdateDto, imageFile);
			return new ResponseEntity<>(updateAdmin, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	
	
	

}
