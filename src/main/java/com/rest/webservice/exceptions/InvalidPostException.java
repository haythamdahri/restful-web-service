package com.rest.webservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class InvalidPostException extends RuntimeException {

	public InvalidPostException() {
	}

	public InvalidPostException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public InvalidPostException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidPostException(String message) {
		super(message);
	}

	public InvalidPostException(Throwable cause) {
		super(cause);
	}

	
	
}
