package com.rest.webservice.service;

import java.util.Collection;

import com.rest.webservice.entities.Post;
import com.rest.webservice.exceptions.PostNotFoundException;

/**
 * @author HAYTHAM DAHRI
 * Post service specification 
 */
public interface PostService {

	/**
	 * Persist the passed post in the database 
	 */
	public Post savePost(Post post) throws Exception;
	
	/**
	 * Fetch a post from database 
	 * @param id
	 */
	public Post getPost(Long id) throws PostNotFoundException;
	
	/**
	 * Fetch a post from database 
	 * @param id
	 */
	public Boolean deletePost(Long id);
	
	/**
	 * Fetch all posts from database 
	 */
	public Collection<Post> getPosts();
	
	/**
	 * Fetch user posts from database 
	 */
	public Collection<Post> getPosts(Long userId);
	
}
