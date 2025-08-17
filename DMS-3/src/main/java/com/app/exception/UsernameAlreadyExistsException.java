package com.app.exception;

import org.springframework.web.bind.annotation.ResponseStatus;


public class UsernameAlreadyExistsException extends RuntimeException  {
	public UsernameAlreadyExistsException(String username) {
        super("Username '" + username + "' is already taken.");
    }

}
