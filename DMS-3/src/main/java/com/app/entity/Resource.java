package com.app.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.app.enums.ResourceKind;
import com.app.enums.ResourceStatus;
import com.app.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Resource {
    
     @Id
     private String id;

     @Enumerated(EnumType.STRING)
     private ResourceKind kind;

     private String type;            // e.g. "Type‑1 Medical Team", "5000 L Water Unit"
     
     private String name;            // human‑readable
     
     private String phoneNumber;
     
     @Column(nullable = false, unique = true)
     private String username;
        
     private String password;
     
     private Integer capacity;       // numeric estimate (e.g. staff count, liters)
     
     private String city;        // city or warehouse
     
     @Enumerated(EnumType.STRING)
     private ResourceStatus status;  // AVAILABLE, ASSIGNED, MAINTENANCE, RETURNED, etc.
     
     private String description;
     
     @Enumerated(EnumType.STRING)
     private Role role;
     
     //supporting document if any
     private String imageName;
     private String imageType;
     @Lob
     private byte[] imageData;
     
     @ManyToMany(mappedBy = "resources")
     @JsonIgnore
     private List<Alert> alerts = new ArrayList<>();
     
     private boolean active;
     private String otp;
     private LocalDateTime otpGeneratedTime;
     
     private boolean verified;
}