package com.rest.webservice.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author HAYTHAM DAHRI User entity class
 */
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

	/**
	 * User identifier
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@NotNull(message = "Name cannot be null")
	@NotEmpty(message = "Name cannot be empty")
	@Column(name = "name", nullable = false, insertable = true, updatable = true, unique = true)
	private String name;

	@Past(message = "Invalid birth date")
	@Temporal(TemporalType.DATE)
	@Column(name = "birth_date", nullable = false, insertable = true, updatable = true)
	private Date birthDate;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnore
	private Collection<Post> posts;
	
	// Convenient method to add a new post to the current user
	public void addPost(Post post) {
		// Check if collection is null
		if( this.posts == null ) {
			this.posts = new ArrayList<>();
		}
		// Add the passed post
		this.posts.add(post);
	}

}
