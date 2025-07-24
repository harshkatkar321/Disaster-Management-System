package com.app.config;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;

import java.net.Authenticator;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.n52.jackson.datatype.jts.JtsModule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

@Configuration
public class SecurityConfig {
	
	@Value("${security.jwt.secret}")
	private String secretKey;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public JtsModule jtsModule() {
	    return new JtsModule();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration cfg) throws Exception{
		
		return cfg.getAuthenticationManager();
	}
	
	@Bean
	public JwtDecoder jwtDecoder() {
		
		byte[] secreteBytes = Base64.getDecoder().decode(secretKey.getBytes());
		SecretKey hmacKey = new SecretKeySpec(secreteBytes, "HmacSHA256");
		return NimbusJwtDecoder.withSecretKey(hmacKey).build();
	}
	
	@Bean
	public JwtEncoder jwtEncoder() {
		
		byte[] secreteBytes = Base64.getDecoder().decode(secretKey.getBytes());
		SecretKey hmacKey = new SecretKeySpec(secreteBytes, "HmacSHA256");
		return new NimbusJwtEncoder(new ImmutableSecret<>(hmacKey));
	}
	
//	@Bean
//	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
//		
//		return http
//				.csrf(csrf -> csrf.disable())
//				.authorizeHttpRequests(
//								auth -> auth.requestMatchers("/api/v1/login", "/api/v1/register").permitAll()
//								
//								// USER access
//		                        .requestMatchers("/users/**").hasRole("USER")
//		                        .requestMatchers(HttpMethod.POST, "/api/disasters/**").hasRole("USER")
//		                        
//		                        // ADMIN + SUPER_ADMIN access to /disasters/**
//		                        .requestMatchers("/api/disasters/**").hasAnyRole("ADMIN", "SUPER_ADMIN")
//		                        
//		                     // ADMIN + SUPER_ADMIN can access PUT methods under /admin/**
//		                        .requestMatchers(HttpMethod.PUT, "/api/admin/**").hasAnyRole("ADMIN", "SUPER_ADMIN")
//		                        
//		                     // Only SUPER_ADMIN can access all other /admin/** requests
//		                        .requestMatchers("/api/admin/**").hasRole("SUPER_ADMIN")
//		                        
//		                     // SUPER_ADMIN-only for /super-admin/**
//		                        .requestMatchers("/api/super-admin/**").hasRole("SUPER_ADMIN")
//								
//								.anyRequest().authenticated()
//						).oauth2ResourceServer(
//								rs -> rs.jwt(Customizer.withDefaults())
//						).sessionManagement(
//								session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//						)
//				.build();
//	}
	
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    return http
	      .csrf(csrf -> csrf.disable())
	      .authorizeHttpRequests(auth -> auth
	          .requestMatchers("/api/v1/login", "/api/v1/register").permitAll()
	          .requestMatchers("/users/**").hasAnyRole("USER","ADMIN", "SUPER_ADMIN")
	          .requestMatchers(HttpMethod.POST, "/api/disasters/**").hasAnyRole("USER","ADMIN", "SUPER_ADMIN")
	          .requestMatchers("/api/disasters/**").hasAnyRole("ADMIN", "SUPER_ADMIN")
	          .requestMatchers("/api/guidelines/**").hasAnyRole("ADMIN", "SUPER_ADMIN")
	          .requestMatchers(HttpMethod.GET, "/api/alerts/**").hasAnyRole("USER","ADMIN", "SUPER_ADMIN")
	          .requestMatchers("/api/alerts/**").hasAnyRole("ADMIN", "SUPER_ADMIN")
	          .requestMatchers(HttpMethod.PUT, "/api/admin/**").hasAnyRole("ADMIN", "SUPER_ADMIN")
	          .requestMatchers("/api/admin/**").hasRole("SUPER_ADMIN")
	          .requestMatchers("/api/super-admin/**").hasRole("SUPER_ADMIN")
	          .anyRequest().authenticated()
	      )
	      .oauth2ResourceServer(rs -> rs
	          .jwt(jwt -> jwt
	              .jwtAuthenticationConverter(JwtAuthConverter.jwtAuthenticationConverter())
	          )
	      )
	      .sessionManagement(session -> session
	          .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	      )
	      .build();
	}

}
