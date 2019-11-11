package com.rest.webservice.dao;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rest.webservice.entities.Post;

/**
 * @author HAYTHAM DAHRI
 * Post data accessor object 
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

	public Collection<Post> findByUserId(Long userId);
	
}
