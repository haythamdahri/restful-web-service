package com.rest.webservice.controllers;

import java.net.URI;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.rest.webservice.entities.Post;
import com.rest.webservice.entities.User;
import com.rest.webservice.exceptions.InvalidUserException;
import com.rest.webservice.exceptions.UserNotFoundException;
import com.rest.webservice.service.PostService;
import com.rest.webservice.service.UserService;

import net.minidev.json.JSONObject;

/**
 * @author HAYTHAM DAHRI User Rest ontroller
 */
@RestController
@RequestMapping(path = "/")
public class UserRestController {

	/**
	 * Inject instance of UserService class
	 */
	@Autowired
	private UserService userService;
	
	/**
	 * Inject instance of PostService class
	 */
	@Autowired
	private PostService postService;


	/**
	 * Retrieve users from database using dao object
	 */
	@RequestMapping(path = "users", method = { RequestMethod.GET})
	public ResponseEntity<?> getAllUsers() throws Exception {
		// Fetch all users
		return new ResponseEntity<Object>(this.userService.getUsers(), HttpStatus.OK);
	}

	/**
	 * Persist user in the database using user service object Redirect client to the
	 * created user uri
	 */
	@RequestMapping(path = "users", method = { RequestMethod.POST, RequestMethod.PUT })
	public ResponseEntity<?> saveTheUser(@RequestBody User user) throws Exception {
		// Persist user
		user = this.userService.saveUser(user);
		// Build and redirect client to the created user uri
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{criteria}").buildAndExpand(user.getId())
				.toUri();
		return ResponseEntity.created(location).build();
	}

	/**
	 * Retrieve a user from the database using user service object 
	 */
	@RequestMapping(path = "users/{criteria}", method = {RequestMethod.GET})
	public User getTheUser(@PathVariable(value ="criteria", required = true) String criteria) throws UserNotFoundException{
		// Convert criteria to userId
		Long userId;
		try {
			userId = Long.parseLong(criteria);
			/**
			 * Check if user exists
			 * An exception will be thrown if user does not exist
			 */
			return this.userService.getUser(userId);
		}
		catch(Exception ex) {
			// Retrieve user by name
			return this.userService.getUser(criteria);
		}
	}

	/**
	 * Delete a user from the database using user service object
	 */
	@RequestMapping(path = "users/{criteria}", method = { RequestMethod.DELETE })
	public ResponseEntity<?> deleteTheUser(@PathVariable(value = "criteria", required = true) String criteria)
			throws UserNotFoundException {
		// Convert criteria to userId
		Long userId;
		try {
			userId = Long.parseLong(criteria);
			/**
			 * Check if user exists
			 * An exception will be thrown if user does not exist
			 */
			userId = this.userService.getUser(userId).getId();
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
			// Retrieve user by name
			userId = this.userService.getUser(criteria).getId();
		}
		// Fetch the user using the user service object
		this.userService.deleteUser(userId);
		// Build response object
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("Status", HttpStatus.OK);
		jsonObject.put("Message", "User with id: " + userId + " has been deleted successfully, all user related posts has been deleted too.");
		jsonObject.put("TimeStamp", new Date());
		// Return json object
		return new ResponseEntity<Object>(jsonObject, HttpStatus.OK);
	}
	

	/**
	 * Retrieve a user posts from the database using user service and post service objects
	 */
	@RequestMapping(path = "users/{criteria}/posts", method = {RequestMethod.GET})
	public ResponseEntity<?> getUserPosts(@PathVariable(value ="criteria", required = true) String criteria) throws UserNotFoundException{
		Long userId = null;
		// Convert criteria into long if possible
		try {
			userId = this.userService.getUser(Long.parseLong(criteria)).getId();
		}
		catch(Exception ex) {
			// Fetch the user using the user service
			userId = this.userService.getUser(criteria).getId();
		}
		// Fetch posts
		Collection<Post> posts = this.postService.getPosts(userId);
		// Return the response
		return new ResponseEntity<Object>(posts, HttpStatus.OK);
	}
	

}
