package com.rest.webservice.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rest.webservice.entities.User;

/**
 * @author HAYTHAM DAHRI
 * User data accessor object 
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	/**
	 * Custom method to retrieve a user
	 * @param name 
	 */
	public Optional<User> findByNameIgnoreCase(@Param(value = "name") String name);
	
}
