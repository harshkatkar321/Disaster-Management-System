package com.app.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.UpdateTimestamp;

import com.app.enums.AlertSeverity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Alert {
	
	@Id
	@Column(name = "alert_id")
	private String id;
	
	private String type;
	
	private String location;
	
	@Column(length = 1000)
    private String description;
	
	@ManyToOne
    @JoinColumn(name = "disaster_id", nullable = false)
    private Disaster disaster;
	
	@UpdateTimestamp
    private LocalDateTime updatedAt;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "admin_id")
	private Admin admin;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

//	private String adminId;
//	
//	private String userId;
	
	@Enumerated(EnumType.STRING)
    private AlertSeverity severity;

    // Additional custom fields
    private String region;
    
    private double riskScore;
    
    @Column(length = 1000)
    private String message;
    
    @ElementCollection
    @CollectionTable(
        name = "alert_tags",
        joinColumns = @JoinColumn(name = "alert_id")  // FK referencing alerts.alert_id
    )
    @Column(name = "tag")  // The column storing each tag string
    private List<String> tags = new ArrayList<>();
}
