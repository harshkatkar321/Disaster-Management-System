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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.app.dto.LoginRequest;
import com.app.dto.LoginResponse;
import com.app.dto.RegisterUserDto;
import com.app.dto.ResourceDto;
import com.app.dto.SetNewPasswordDto;
import com.app.dto.VerifyAccountDto;
import com.app.entity.Resource;
import com.app.entity.User;
import com.app.service.JwtService;
import com.app.service.MyUserService;
import com.app.service.ResourceService;
import com.app.service.UserService;
import com.app.service.VerificationService;

import jakarta.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class AuthController {
	
	private final AuthenticationManager authenticationManager;
	
	private final JwtService jwtService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ResourceService resourceService;
	
	@Autowired
	private MyUserService myUserService;
	
	@Autowired
	private VerificationService verificationService;
	
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
	
	@PostMapping("/resource")
	public ResponseEntity<?> registerResource(@RequestPart @Valid ResourceDto dto, BindingResult br,
			@RequestPart(required = false) MultipartFile imageFile ){
		
		if (br.hasErrors()) {
	        var errors = br.getFieldErrors().stream()
	            .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
	            .toList();
	        return ResponseEntity.badRequest().body(Map.of("errors", errors));
	    }
		
		try {
			Resource resource = resourceService.addResource(dto, imageFile);
			return new ResponseEntity<>(resource, HttpStatus.CREATED);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@PostMapping("/verifyaccount")
	public ResponseEntity<?> verifyAccount(@RequestBody VerifyAccountDto dto){
		
		try {
			boolean result = verificationService.verifyAccout(dto.getEmail(), dto.getOtp());
			if(result) {
				String message = "Otp Verified Successfulyy!  You Can login";
				return new ResponseEntity<>(message, HttpStatus.OK);
			}
			else {
				String message = "Otp verification failed Please provide correct otp! or Regenerate otp";
				return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@PostMapping("/regenerateotp")
	public ResponseEntity<?> reGenerateOtp(@RequestBody VerifyAccountDto dto){
		try {
			boolean result = verificationService.reGenerateOtp(dto.getEmail());
			
			if(result) {
				String message = "Otp Regenerated successfully please verify within 5 minutes!!";
				return new ResponseEntity<>(message, HttpStatus.OK);
			}
			else {
				String message = "You are already verified! Login with your Credentials";
				return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/forgotpasswordotp")
	public ResponseEntity<?> forgotPassword(@RequestBody VerifyAccountDto dto){
		
		try {
			boolean result = verificationService.forgotPasswordOtp(dto.getEmail());
			if(result) {
				String message = "Otp to set new Password is shared on your email id";
				return new ResponseEntity<>(message, HttpStatus.OK);
			}
			else {
				String message = "You are not a verified your Please verify your Email to get the Otp ";
				return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/setNewPassword")
	public ResponseEntity<?> setNewPassword(@RequestBody @Valid SetNewPasswordDto dto, BindingResult br){
		
		if (br.hasErrors()) {
	        var errors = br.getFieldErrors().stream()
	            .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
	            .toList();
	        return ResponseEntity.badRequest().body(Map.of("errors", errors));
	    }
		
		try {
			boolean result = verificationService.setNewPassword(dto);
			if(result) {
				String message = "Password changed successfully!! Your can login with new password";
				return new ResponseEntity<>(message, HttpStatus.OK);
			}
			else {
				String message = "Invalid otp or you are not verified your please verify email first to set new password ";
				return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	

}
