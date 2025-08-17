package com.app.dto;

import com.app.enums.ResourceKind;
import com.app.enums.ResourceStatus;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResourceDto {
	
//	@NotBlank(message = "Resourcekind is required")
	@Enumerated(EnumType.STRING)
	 private ResourceKind kind;
	
	@NotBlank(message = "Type is required")
	private String type;            // e.g. "Type‑1 Medical Team", "5000 L Water Unit"
	 
	@NotBlank(message = "Name is required")
	 private String name;            // human‑readable
	
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
	 
	 private Integer capacity;       // numeric estimate (e.g. staff count, liters)
	 
	 @NotBlank(message = "city is required")
	 private String city;        // city or warehouse
	 
	 private String description;

}
