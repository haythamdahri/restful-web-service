package com.rest.webservice.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rest.webservice.dao.PostRepository;
import com.rest.webservice.entities.Post;
import com.rest.webservice.exceptions.PostNotFoundException;

/**
 * @author HAYTHAM DAHRI Post service implementation
 */
@Service
public class PostServiceImpl implements PostService {

	/**
	 * Inject instance of post dao
	 */
	@Autowired
	private PostRepository postRepository;

	@Override
	public Post savePost(Post post) throws Exception {
		// Save the post and return the new persisted version of it
		return this.postRepository.save(post);
	}

	@Override
	public Post getPost(Long id) throws PostNotFoundException {
		// Return the retrieved post or null if not found
		return this.postRepository.findById(id)
				.orElseThrow(() -> new PostNotFoundException("No post has been found with id: " + id));
	}

	@Override
	public Boolean deletePost(Long id) {
		// Delete post from database usig dao object
		this.postRepository.deleteById(id);
		// Return response
		return true;
	}

	@Override
	public Collection<Post> getPosts() {
		// Return all posts using dao object
		return this.postRepository.findAll();
	}

	@Override
	public Collection<Post> getPosts(Long userId) {
		// Return user posts using dao object
		return this.postRepository.findByUserId(userId);
	}

}
