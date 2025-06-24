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

import com.app.dto.UserDto;
import com.app.dto.UserUpdateDto;
import com.app.entity.User;
import com.app.service.AdminService;

@RestController
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	
	@PostMapping("/admin")
	public User createAdmin(@RequestBody UserDto userDto) {
		return adminService.addAdmin(userDto);
	}
	
	@GetMapping("/admin")
	public List<User> getAllAdmin(){
		return adminService.getAllAdmin();
	}
	
	@GetMapping("/admin/{id}")
	public User getAdmin(@PathVariable String id) {
		return adminService.getAdmin(id);
	}

	@DeleteMapping("/admin/{id}")
	public boolean deleteAdmin(@PathVariable String id) {
		return adminService.deleteAdmin(id);
	}
	
	@PutMapping("/admin/{id}")
	public User updateAdmin(@PathVariable String id, @RequestBody UserUpdateDto userUpdateDto) {
		return adminService.updateAdmin(id, userUpdateDto);
	}
}
