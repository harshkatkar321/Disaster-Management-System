package com.app.util;

import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class OtpUtil {
	
	public String generateOtp() {
		Random random = new Random();
		int randamNumber = random.nextInt(999999);
		String output = Integer.toString(randamNumber);
		
		while(output.length()<6) {
			output="0"+output;
		}
		return output;
	}

}
