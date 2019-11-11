package com.rest.webservice.controllers;

import java.net.URI;
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
import com.rest.webservice.exceptions.InvalidPostException;
import com.rest.webservice.exceptions.PostNotFoundException;
import com.rest.webservice.service.PostService;

import net.minidev.json.JSONObject;

/**
 * @author HAYTHAM DAHRI Post Rest ontroller
 */
@RestController
@RequestMapping(path = "/")
public class PostRestController {

	/**
	 * Inject instance of PostService class
	 */
	@Autowired
	private PostService postService;

	/**
	 * Retrieve posts from database using dao object
	 */
	@RequestMapping(path = "posts", method = { RequestMethod.GET})
	public ResponseEntity<?> getAllPosts() throws Exception {
		// Fetch all users
		return new ResponseEntity<Object>(this.postService.getPosts(), HttpStatus.OK);
	}

	/**
	 * Persist post in the database using post service object Redirect client to the
	 * created post uri
	 */
	@RequestMapping(path = "posts", method = { RequestMethod.POST, RequestMethod.PUT })
	public ResponseEntity<?> saveThepost(@RequestBody Post post) throws Exception {
		// Persist post
		post = this.postService.savePost(post);
		// Build and redirect client to the created post uri
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{criteria}").buildAndExpand(post.getId())
				.toUri();
		return ResponseEntity.created(location).build();
	}

	/**
	 * Retrieve a post from the database using post service object 
	 */
	@RequestMapping(path = "posts/{criteria}", method = {RequestMethod.GET})
	public ResponseEntity<?> getThePost(@PathVariable(value ="criteria", required = true) String criteria) throws PostNotFoundException{
		Long postId = null;
		// Convert criteria into long if possible
		try {
			postId = Long.parseLong(criteria);
		}
		catch(Exception ex) {
			// Throw an exception of type InvalidPostException
			throw new InvalidPostException(criteria + " is not a valid post id");
		}
		// Retrieve post if found
		Post post = this.postService.getPost(postId);
		// Return response
		return new ResponseEntity<>(post, HttpStatus.OK);
	}

	/**
	 * Delete a post from the database using post service object
	 */
	@RequestMapping(path = "posts/{criteria}", method = { RequestMethod.DELETE })
	public ResponseEntity<?> deleteThePost(@PathVariable(value = "criteria", required = true) String criteria)
			throws PostNotFoundException {
		// Convert criteria to PostId
		Long postId;
		try {
			postId = Long.parseLong(criteria);
		}
		catch(Exception ex) {
			// throw InvalidPostException
			throw new InvalidPostException(criteria + " is not a valid post id");
		}
		/**
		 * Check if post exists
		 * An exception will be thrown if post does not exist
		 */
		this.postService.getPost(postId);
		// Fetch the post using the post service object
		this.postService.deletePost(postId);
		// Build response object
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("Status", HttpStatus.OK);
		jsonObject.put("Message", "Post with id: " + postId + " has been deleted successfully");
		jsonObject.put("TimeStamp", new Date());
		// Return json object
		return new ResponseEntity<Object>(jsonObject, HttpStatus.OK);
	}
	
	

}
