package com.rest.webservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class RoleNotFoundException extends RuntimeException {

	public RoleNotFoundException() {
	}

	public RoleNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public RoleNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public RoleNotFoundException(String message) {
		super(message);
	}

	public RoleNotFoundException(Throwable cause) {
		super(cause);
	}

	
	
}
