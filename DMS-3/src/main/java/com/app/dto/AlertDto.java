package com.app.dto;

import java.util.List;

import com.app.enums.AlertSeverity;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlertDto {
	
	
    
    @NotBlank(message = "Type is required")
    private String type;

    @NotBlank(message = "Location is required")
    private String location;

    @NotBlank(message = "Description is required")
    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String description;

    @NotBlank(message = "Disaster ID is required")
    private String disasterId;

    // adminId and userId are optional, depending on your logic
    private String adminId;
    private String userId;

    @NotNull(message = "Severity is required")
    private AlertSeverity severity;

    @NotBlank(message = "Region is required")
    private String region;

    @Min(value = 0, message = "Risk score must be at least 0")
    private double riskScore;

    @NotBlank(message = "Message is required")
    @Size(max = 1000, message = "Message cannot exceed 1000 characters")
    private String message;

    @NotNull(message = "Tags list must be present (can be empty)")
    @Size(min = 0, message = "Tags list cannot be null")
    private List<@NotBlank(message = "Tags cannot be blank") String> tags;

}
