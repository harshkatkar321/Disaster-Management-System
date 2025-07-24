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

import com.app.dto.MailDto;
import com.app.dto.UpdatUserDto;
import com.app.entity.User;
import com.app.service.MyUserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserController {
	
	@Autowired
	private MyUserService myUserService;
	
	@GetMapping("/users")
	public ResponseEntity<?> getAllUsers(){
		try {
			List<User> users = myUserService.getUsers();
			return new ResponseEntity<>(users, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NO_CONTENT);
		}
	}
	
	@GetMapping("/users/{username}")
	public ResponseEntity<?> getUser(@PathVariable String username){
	    try {
			User user = myUserService.getUser(username);
			return new ResponseEntity<>(user, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NO_CONTENT);
		}
	 }
	
	@DeleteMapping("/users/{id}")
	 public ResponseEntity<?> deleteUser(@PathVariable String id) {
		 try {
			 myUserService.deleteUser(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NO_CONTENT);
		}
	 }
	
	 @PutMapping("/users/{id}")
	 public ResponseEntity<?> updateUser(@PathVariable String id, @RequestPart @Valid UpdatUserDto updatUserDto, BindingResult br,
			 @RequestPart(required = false) MultipartFile imageFile) {
		 
		 if (br.hasErrors()) {
		        var errors = br.getFieldErrors().stream()
		            .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
		            .toList();
		        return ResponseEntity.badRequest().body(Map.of("errors", errors));
		    }
		 
		 try {
			User updateUser = myUserService.updateUser(id, updatUserDto, imageFile);
			return new ResponseEntity<>(updateUser, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	 }
	
	@GetMapping("/users/city/{city}")
	 public ResponseEntity<?> findByCity(@PathVariable String city){
		 try {
			List<User> byLocation = myUserService.findByLocation(city);
			return new ResponseEntity<>(byLocation, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NO_CONTENT);
		}
	 }
	
	@GetMapping("/users/phonenumber/{city}")
	public ResponseEntity<?> findPhoneNumberByCity(@PathVariable String city){
		
		try {
			List<String> phoneNumberByCity = myUserService.findPhoneNumberByCity(city);
			return new ResponseEntity<>(phoneNumberByCity, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/users/email/{city}")
	public ResponseEntity<?> findEmailsByCity(@PathVariable String city){
		
		try {
			List<String> emailByCity = myUserService.findEmailByCity(city);
			return new ResponseEntity<>(emailByCity, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/users/mail/{city}")
	public ResponseEntity<?> sendMail(@PathVariable String city, @RequestBody MailDto dto,  BindingResult br){
		
		 if (br.hasErrors()) {
		        var errors = br.getFieldErrors().stream()
		            .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
		            .toList();
		        return ResponseEntity.badRequest().body(Map.of("errors", errors));
		    }
		
		try {
			myUserService.sendMailByCity(city, dto);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

}
