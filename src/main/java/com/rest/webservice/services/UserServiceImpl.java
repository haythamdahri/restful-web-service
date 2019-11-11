package com.rest.webservice.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rest.webservice.dao.UserRepository;
import com.rest.webservice.entities.User;
import com.rest.webservice.exceptions.UserNotFoundException;

/**
 * @author HAYTHAM DAHRI User service implementation
 */
@Service
public class UserServiceImpl implements UserService {

	/**
	 * Inject instance of user dao
	 */
	@Autowired
	private UserRepository userRepository;

	@Override
	public User saveUser(User user) throws Exception {
		// Save the user and return the new persisted version of it
		return this.userRepository.save(user);
	}

	@Override
	public User getUser(Long id) throws UserNotFoundException{
		// Return the retrieved user or null if not found
		return this.userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("No user has been found with id: " + id));
	}

	@Override
	public User getUser(String name) throws UserNotFoundException{
		// Return the retrieved user or null if not found
		return this.userRepository.findByNameIgnoreCase(name).orElseThrow(() -> new UserNotFoundException("No user has been found with name: " + name));
	}

	@Override
	public Boolean deleteUser(Long id) {
		// Delete user from database usig dao object
		this.userRepository.deleteById(id);
		// Return response
		return true;
	}

	@Override
	public Collection<User> getUsers() {
		// Return all users using dao object
		return this.userRepository.findAll();
	}

}
