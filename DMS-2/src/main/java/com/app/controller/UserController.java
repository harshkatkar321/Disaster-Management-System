package com.app.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.UserDto;
import com.app.dto.UserLoginDto;
import com.app.dto.UserUpdateDto;
import com.app.entity.User;
import com.app.service.UserService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/register")
    public ResponseEntity<User> newUser(@RequestBody UserDto userDto){
        User newUser = userService.addUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }
	
	@GetMapping("/users")
	public List<User> getAllUsers(){
		return userService.getUsers();
	}
	
	 @GetMapping("/users/{id}")
	public User getUser(@PathVariable String id){
	    return userService.getUser(id);
	 }
	 
	 @DeleteMapping("/users/{id}")
	 public void deleteUser(@PathVariable String id) {
		 userService.deleteUser(id);
	 }
	 
	 @PutMapping("/users/{id}")
	 public User updateUser(@PathVariable String id, @RequestBody UserUpdateDto userUpdateDto) {
		 return userService.updateUser(id, userUpdateDto);
	 }
	 
	 @GetMapping("/users/location/{location}")
	 public List<User> findByLocation(@PathVariable String location){
		 return userService.findByLocation(location);
	 }
	
	 
	 @PostMapping("/login")
	 public ResponseEntity<String> login(@RequestBody UserLoginDto userLoginDto, HttpSession session){
		 try {
			boolean isAuthenticated = userService.authenticate(userLoginDto.getEmail(), userLoginDto.getPassword());
			
			if(isAuthenticated)
			{
				session.setAttribute("user", userLoginDto.getEmail());
				return ResponseEntity.ok("Login Successfull");
			}
			else
			{
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Username or Password");
			}
		} catch (UsernameNotFoundException e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	    } catch (BadCredentialsException e) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
	    }
	 }

}
