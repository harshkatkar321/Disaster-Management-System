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
public class ChangePasswordDto {
	
	@NotBlank(message = "Email is required")
    @Email(message = "Email format is invalid")
	private String email;
	
	@NotBlank(message = "Old Password is required")
    @Size(min = 8, max = 64, message = "Old Password must be between 8 and 64 characters")
    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
        message = "Old Password must contain uppercase, lowercase, number, and special character"
    )
	private String oldPassword;
	
	@NotBlank(message = "New Password is required")
    @Size(min = 8, max = 64, message = "New Password must be between 8 and 64 characters")
    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
        message = "New Password must contain uppercase, lowercase, number, and special character"
    )
	private String newPassword;

}
