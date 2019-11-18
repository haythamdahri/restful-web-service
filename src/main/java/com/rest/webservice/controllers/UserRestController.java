package com.rest.webservice.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.rest.webservice.entities.Post;
import com.rest.webservice.entities.User;
import com.rest.webservice.exceptions.UserNotFoundException;
import com.rest.webservice.services.PostService;
import com.rest.webservice.services.UserService;

import net.minidev.json.JSONObject;

/**
 * @author HAYTHAM DAHRI User Rest ontroller
 */
@RestController
@RequestMapping(path = "/", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.TEXT_PLAIN_VALUE})
//@Secured(value = {"ROLE_ADMIN"})
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
	 * Inject instance of MessageSource class
	 */
	@Autowired
	private MessageSource messageSource;

	/**
	 * Retrieve users from database using dao object
	 */
	@RequestMapping(path = "users", method = { RequestMethod.GET })
	public ResponseEntity<?> getAllUsers() throws Exception {
		// Build hateaos object, EntityModel
		Collection<EntityModel<User>> users = StreamSupport.stream(this.userService.getUsers().spliterator(), false)
				.map(user -> {
					EntityModel<User> entityUser = null;
					try {
						entityUser = new EntityModel<>(user,
								linkTo(methodOn(this.getClass()).getAllUsers()).withRel("all-users"),
								linkTo(methodOn(this.getClass()).getTheUser(user.getId().toString())).withSelfRel(),
								linkTo(methodOn(this.getClass()).getUserPosts(user.getId().toString()))
										.withRel("user-posts"));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return entityUser;
				}).collect(Collectors.toList());
		// Fetch all users
		return new ResponseEntity<Object>(users, HttpStatus.OK);
	}

	/**
	 * Persist user in the database using user service object Redirect client to the
	 * created user uri
	 */
	@RequestMapping(path = "users", method = { RequestMethod.POST, RequestMethod.PUT })
	public ResponseEntity<?> saveTheUser(@Valid @RequestBody User user) throws Exception {
		// Persist user
		user = this.userService.saveUser(user);
		// Build and redirect client to the created user uri
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{criteria}").buildAndExpand(user.getId())
				.toUri();
		return ResponseEntity.created(location).build();
	}

	/**
	 * Retrieve a user from the database using user service object
	 * 
	 * @throws Exception
	 */
	@RequestMapping(path = "users/{criteria}", method = { RequestMethod.GET })
	public ResponseEntity<?> getTheUser(@PathVariable(value = "criteria", required = true) String criteria)
			throws Exception {
		User user = null;
		// Convert criteria to userId
		Long userId;
		try {
			userId = Long.parseLong(criteria);
			/**
			 * Check if user exists An exception will be thrown if user does not exist
			 */
			user = this.userService.getUser(userId);
		} catch (Exception ex) {
			// Retrieve user by name
			user = this.userService.getUser(criteria);
		}

		// HATEOAS
		EntityModel<User> userEntity = new EntityModel<>(user,
				linkTo(methodOn(this.getClass()).getTheUser(user.getId().toString())).withSelfRel(),
				linkTo(methodOn(this.getClass()).getUserPosts(user.getId().toString())).withRel("user-posts"),
				linkTo(methodOn(this.getClass()).getAllUsers()).withRel("all-users"));

		return new ResponseEntity<Object>(userEntity, HttpStatus.OK);

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
			 * Check if user exists An exception will be thrown if user does not exist
			 */
			userId = this.userService.getUser(userId).getId();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			// Retrieve user by name
			userId = this.userService.getUser(criteria).getId();
		}
		// Fetch the user using the user service object
		this.userService.deleteUser(userId);
		// Build response object
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("Status", HttpStatus.OK);
		jsonObject.put("Message", "User with id: " + userId
				+ " has been deleted successfully, all user related posts has been deleted too.");
		jsonObject.put("TimeStamp", new Date());
		// Return json object
		return new ResponseEntity<Object>(jsonObject, HttpStatus.OK);
	}

	/**
	 * Retrieve a user posts from the database using user service and post service
	 * objects
	 */
	@RequestMapping(path = "users/{criteria}/posts", method = { RequestMethod.GET })
	public ResponseEntity<?> getUserPosts(@PathVariable(value = "criteria", required = true) String criteria)
			throws UserNotFoundException {
		Long userId = null;
		// Convert criteria into long if possible
		try {
			userId = this.userService.getUser(Long.parseLong(criteria)).getId();
		} catch (Exception ex) {
			// Fetch the user using the user service
			userId = this.userService.getUser(criteria).getId();
		}
		// Fetch posts
		Collection<Post> posts = this.postService.getPosts(userId);
		// Return the response
		return new ResponseEntity<Object>(posts, HttpStatus.OK);
	}

	/**
	 * Hello world internationalized method
	 */
	@RequestMapping(path = "hello-world-internationalized", method = RequestMethod.GET)
	public String helloWorldInternationalized() {
		return this.messageSource.getMessage("helloworld.message", null, LocaleContextHolder.getLocale());
	}

}
