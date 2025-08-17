package com.app.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class GuidelineDto {
	
	@NotBlank(message = "message must be required")
	private String message;
	
	@NotBlank(message = "keyword must be required")
	private String keyword;

}
