package com.app.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MailDto {
	
	@NotBlank(message = "Subject is required!")
	private String subject;
	
	@NotBlank(message = "body is required!")
	private String body;

}
