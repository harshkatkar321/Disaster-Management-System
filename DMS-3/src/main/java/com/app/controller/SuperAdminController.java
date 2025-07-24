package com.app.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.app.dto.OwnershipTransferDto;
import com.app.dto.UpdatUserDto;
import com.app.entity.User;
import com.app.enums.Role;
import com.app.repository.UserRepository;
import com.app.service.SuperAdminService;
import com.app.service.UserService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
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
		
		User currentSupperAdmin = userRepository.findByUsername(ownershipTransferDto.getCurrentOwnerUsername())
				.orElseThrow(()-> new EntityNotFoundException("Current Super Admin Not Found"));
		
		User newOwner = userRepository.findByUsername(ownershipTransferDto.getNewOwnerUsername())
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
	public ResponseEntity<?> updateSuperAdmin(@PathVariable String id, @RequestPart @Valid UpdatUserDto userUpdateDto,
			BindingResult br,
			@RequestPart MultipartFile imageFile){
		
		 if (br.hasErrors()) {
		        var errors = br.getFieldErrors().stream()
		            .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
		            .toList();
		        return ResponseEntity.badRequest().body(Map.of("errors", errors));
		    }
		 
		 try {
			User updateUser = superAdminService.updateUser(id, userUpdateDto, imageFile);
			return new ResponseEntity<>(updateUser, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
//		return superAdminService.updateUser(id, userUpdateDto,imageFile);
	}

}
