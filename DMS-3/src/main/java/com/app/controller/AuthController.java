package com.app.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.app.dto.LoginRequest;
import com.app.dto.LoginResponse;
import com.app.dto.RegisterUserDto;
import com.app.entity.User;
import com.app.service.JwtService;
import com.app.service.MyUserService;
import com.app.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class AuthController {
	
	private final AuthenticationManager authenticationManager;
	
	private final JwtService jwtService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MyUserService myUserService;
	
	public AuthController(AuthenticationManager authenticationManager, JwtService jwtService) {
	
		this.authenticationManager = authenticationManager;
		this.jwtService = jwtService;
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid@RequestBody LoginRequest login, BindingResult br) {
		
		if (br.hasErrors()) {
	        // Collect field errors and return 400
	        var errors = br.getFieldErrors().stream()
	            .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
	            .toList();
	        return ResponseEntity.badRequest().body(Map.of("errors", errors));
	    }
		
		//Authenticate the user
		Authentication auth = authenticationManager
				.authenticate(
						new UsernamePasswordAuthenticationToken(
								login.getUsername(),
								login.getPassword()
								)
						);
		
		//Generate the token for the authenticated user
		String token = jwtService.generateToken((UserDetails)auth.getPrincipal());
		
		//return the token as response
//		return new LoginResponse(token);
		return ResponseEntity.status(HttpStatus.OK).body(new LoginResponse(token));
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestPart @Valid RegisterUserDto dto, BindingResult br,
			@RequestPart(required = false) MultipartFile imageFile){
		
		if (br.hasErrors()) {
	        var errors = br.getFieldErrors().stream()
	            .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
	            .toList();
	        return ResponseEntity.badRequest().body(Map.of("errors", errors));
	    }

		try {
			var user = myUserService.addUser(dto, imageFile);
		    return ResponseEntity.status(HttpStatus.CREATED).body(user);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	    
	}
	

}
