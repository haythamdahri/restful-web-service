package com.rest.webservice.service;

import java.util.Collection;

import com.rest.webservice.entities.User;
import com.rest.webservice.exceptions.UserNotFoundException;

/**
 * @author HAYTHAM DAHRI
 * User service specification 
 */
public interface UserService {

	/**
	 * Persist the passed user in the database 
	 */
	public User saveUser(User user) throws Exception;
	
	/**
	 * Fetch a user from database 
	 * @param id
	 */
	public User getUser(Long id) throws UserNotFoundException;
	
	/**
	 * Fetch a user from database 
	 * @param name
	 */
	public User getUser(String name) throws UserNotFoundException;
	
	/**
	 * Fetch a user from database 
	 * @param id
	 */
	public Boolean deleteUser(Long id);
	
	/**
	 * Fetch all users from database 
	 */
	public Collection<User> getUsers();
	
}
