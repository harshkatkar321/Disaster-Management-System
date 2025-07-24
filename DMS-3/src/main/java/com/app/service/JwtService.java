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


//	public String generateToken(UserDetails userDetails) {
//		
//		Instant now = Instant.now();
//		JwtClaimsSet claims = JwtClaimsSet.builder()
//				.issuer("balaji")
//				.issuedAt(now)
//				.expiresAt(now.plus(Duration.ofMinutes(15)))
//				.subject(userDetails.getUsername())
//				.build();
//		
//		JwsHeader header = JwsHeader.with(MacAlgorithm.HS256)
//				.build();
//		
//		return jwtEncoder.encode(
//				JwtEncoderParameters.from(header, claims)
//				).getTokenValue();
//	}
	
//	public String generateToken(UserDetails userDetails) {
//	    Instant now = Instant.now();
//	    JwtClaimsSet.Builder claimsBuilder = JwtClaimsSet.builder()
//	        .issuer("balaji")
//	        .issuedAt(now)
//	        .expiresAt(now.plus(Duration.ofMinutes(15)))
//	        .subject(userDetails.getUsername());
//
//	    // Add roles as a claim named "roles" (a list of strings)
//	    List<String> roles = userDetails.getAuthorities().stream()
//	        .map(GrantedAuthority::getAuthority)
//	        .map(role -> role.startsWith("ROLE_") ? role.substring(5) : role)
//	        .collect(Collectors.toList());
//	    claimsBuilder.claim("roles", roles);
//
//	    JwtClaimsSet claims = claimsBuilder.build();
//
//	    JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();
//
//	    return jwtEncoder.encode(
//	        JwtEncoderParameters.from(header, claims)
//	    ).getTokenValue();
//	}
	
	
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

	    JwtClaimsSet claims = JwtClaimsSet.builder()
	        .issuer("balaji")
	        .issuedAt(now)
	        .expiresAt(now.plus(Duration.ofMinutes(15)))
	        .subject(userDetails.getUsername())
	        .claim("roles", roles)
	        .build();

	    JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();

	    return jwtEncoder.encode(
	        JwtEncoderParameters.from(header, claims)
	    ).getTokenValue();
	}

}
