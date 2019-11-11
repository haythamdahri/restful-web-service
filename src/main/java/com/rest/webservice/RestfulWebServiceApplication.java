package com.rest.webservice;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.rest.webservice.entities.User;
import com.rest.webservice.service.UserService;

@SpringBootApplication
public class RestfulWebServiceApplication implements CommandLineRunner {

	@Autowired
	private UserService userService;
	
	public static void main(String[] args) {
		SpringApplication.run(RestfulWebServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		if( this.userService.getUsers().isEmpty() ) {
			this.userService.saveUser(new User(null, "HAYTHAM DAHRI", new Date(), null));
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
