package com.rest.webservice.handlers;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.rest.webservice.exceptions.InvalidPostException;
import com.rest.webservice.exceptions.InvalidUserException;
import com.rest.webservice.exceptions.PostNotFoundException;
import com.rest.webservice.exceptions.UserNotFoundException;

/**
 * @author HAYTHAM DAHR
 * Generic excpetion handler class 
 */
@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler{
	
	/**
	 * Exception handler method 
	 */
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<?> handleAllExceptions(Exception ex, WebRequest webRequest) {
		// Create ExceptionResponse object
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(), webRequest.getDescription(false));
		// Return the response
		return new ResponseEntity<Object>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * UserNotFoundException, PostNotFoundException handler method 
	 */
	@ExceptionHandler({UserNotFoundException.class, PostNotFoundException.class})
	public final ResponseEntity<?> handleUserNotFoundException(RuntimeException ex, WebRequest webRequest) {
		// Create ExceptionResponse object
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(), webRequest.getDescription(false));
		// Return the response
		return new ResponseEntity<Object>(exceptionResponse, HttpStatus.NOT_FOUND);
	}

	/**
	 * InvalidUserException, InvalidPostException handler method 
	 */
	@ExceptionHandler({InvalidUserException.class, InvalidPostException.class})
	public final ResponseEntity<?> handleInvalidUserException(RuntimeException ex, WebRequest webRequest) {
		// Create ExceptionResponse object
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(), webRequest.getDescription(false));
		// Return the response
		return new ResponseEntity<Object>(exceptionResponse, HttpStatus.NOT_FOUND);
	}
}
