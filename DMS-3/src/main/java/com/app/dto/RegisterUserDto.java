package com.app.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserDto {
	
	@NotBlank(message = "First name is required")
    @Size(min = 3, max = 50, message = "First name must be mininum 3 and at most 50 characters")
	private String firstName;
	
	private String lastName;
	
	@NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Phone number must be 10 digits and start with 6-9")
	private String phoneNumber;
	
	@NotBlank(message = "Email is required")
    @Email(message = "Email format is invalid")
	private String email;
	
	@NotBlank(message = "Password is required")
    @Size(min = 8, max = 64, message = "Password must be between 8 and 64 characters")
    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
        message = "Password must contain uppercase, lowercase, number, and special character"
    )
	private String password;
	
	//city
	@NotBlank(message = "City is required")
	private String city;
	

}
