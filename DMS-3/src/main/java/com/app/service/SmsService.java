package com.app.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;


@Service
public class SmsService {
//	
	private static final String ACCOUNT_SID = "";
	private static final String AUTH_TOKEN = "";
	private static final String from_number= "";
	
//	@Value("${twilio.account-sid}")
//	private static String ACCOUNT_SID;
//    @Value("${twilio.auth-token}") 
//    private static String AUTH_TOKEN;
//    @Value("${twilio.from-number}")
//    private  String from_number;
	
	static {
		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
	}
	
	public void sms(String message, String to_number) {
		
		Message.creator( new PhoneNumber(to_number),
				new PhoneNumber(from_number),
				message).create();
	}

}