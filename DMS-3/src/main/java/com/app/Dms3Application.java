package com.app;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.app.entity.User;
import com.app.repository.UserRepository;

@SpringBootApplication
public class Dms3Application {

	public static void main(String[] args) {
		SpringApplication.run(Dms3Application.class, args);
	}
	
//	@Bean
//	public CommandLineRunner commandLineRunner(UserRepository userRepository, PasswordEncoder passwordEncoder) {
//		
//		return args -> {
//		    User user = new User();
//		    user.setUsername("admin");
//		    user.setPassword(passwordEncoder.encode("admin"));
//		    userRepository.save(user);
//		};
//	}

}
