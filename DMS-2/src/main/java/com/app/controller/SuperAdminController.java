package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.OwnershipTransferDto;
import com.app.dto.UserUpdateDto;
import com.app.entity.Role;
import com.app.entity.User;
import com.app.repository.UserRepository;
import com.app.service.SuperAdminService;
import com.app.service.UserService;

import jakarta.persistence.EntityNotFoundException;

@RestController
public class SuperAdminController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SuperAdminService superAdminService;
	
//	@PreAuthorize("hasRole('SUPER_ADMIN')")
	@PutMapping("/super-admin/transfer")
	public ResponseEntity<String> transferOwnership(@RequestBody OwnershipTransferDto ownershipTransferDto){
		
		User currentSupperAdmin = userRepository.findById(ownershipTransferDto.getCurrentOwnerId())
				.orElseThrow(()-> new EntityNotFoundException("Current Super Admin Not Found"));
		
		User newOwner = userRepository.findById(ownershipTransferDto.getNewOwnerId())
				.orElseThrow(()-> new EntityNotFoundException("User Not found"));
		
		//set new Super Admin
		newOwner.setRole(Role.SUPER_ADMIN);
		userRepository.save(newOwner);
		
		//downgrade current super admin
		currentSupperAdmin.setRole(Role.USER);
		userRepository.save(currentSupperAdmin);
		
		return ResponseEntity.ok("Ownership Transferred to : ");
	}
	
	@PutMapping("/super-admin/{id}")
	public User updateSuperAdmin(@PathVariable String id, @RequestBody UserUpdateDto userUpdateDto){
		return superAdminService.updateUser(id, userUpdateDto);
	}

}
