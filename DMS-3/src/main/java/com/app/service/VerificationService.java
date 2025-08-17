package com.app.service;

import com.app.dto.SetNewPasswordDto;

public interface VerificationService {
	
	boolean forgotPasswordOtp(String email);
	
	boolean setNewPassword(SetNewPasswordDto dto);
	
	public boolean verifyAccout(String email, String otp);
	
	public boolean reGenerateOtp(String email);

}
