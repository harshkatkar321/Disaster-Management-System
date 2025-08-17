package com.app.service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;



@Service
public class JwtService {
	
	private final JwtEncoder jwtEncoder;

	
	public JwtService(JwtEncoder jwtEncoder) {
		super();
		this.jwtEncoder = jwtEncoder;
	}

	
	public String generateToken(UserDetails userDetails) {
	    Instant now = Instant.now();

	    // Extract roles (without the "ROLE_" prefix)
	    List<String> roles = userDetails.getAuthorities().stream()
	        .map(GrantedAuthority::getAuthority)
	        .map(role -> {
	            if (role.startsWith("ROLE_")) {
	                return role.substring("ROLE_".length());
	            }
	            return role;
	        })
	        .collect(Collectors.toList());
	    
	    boolean active = userDetails.isEnabled();

	    JwtClaimsSet claims = JwtClaimsSet.builder()
	        .issuer("balaji")
	        .issuedAt(now)
	        .expiresAt(now.plus(Duration.ofMinutes(15)))
	        .subject(userDetails.getUsername())
	        .claim("roles", roles)
	        .claim("active", active)    // Add your active flag
	        .build();

	    JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();

	    return jwtEncoder.encode(
	        JwtEncoderParameters.from(header, claims)
	    ).getTokenValue();
	}

}
