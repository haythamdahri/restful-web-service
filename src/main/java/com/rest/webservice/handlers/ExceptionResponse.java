package com.rest.webservice.handlers;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author HAYTHAM DAHRI
 * Exception response class 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponse {

	private Date timeStamp;
	private String message;
	private String details;
	
}
