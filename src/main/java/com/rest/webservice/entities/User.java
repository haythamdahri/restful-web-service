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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonIgnoreProperties(value = {"roles"})
public class User {

	/**
	 * User identifier
	 */
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "Name cannot be null")
	@NotEmpty(message = "Name cannot be empty")
	@Length(min = 2, max = 25, message = "Invalid name length")
	@Column(name = "name", nullable = false, insertable = true, updatable = true, unique = true)
	private String name;

	@Id
	@NotNull(message = "Username cannot be null")
	@NotEmpty(message = "Username cannot be empty")
	@Length(min = 2, max = 25, message = "Invalid username length")
	@Column(name = "username", nullable = false, insertable = true, updatable = true, unique = true)
	private String username;

	@NotNull(message = "Password cannot be null")
	@NotEmpty(message = "Password cannot be empty")
	@Length(min = 2, max = 1500, message = "Invalid password length")
	@Column(name = "password", nullable = false, insertable = true, updatable = true, unique = false)
	private String password;

	/**
	 * ManyToMany relationship between users and roles
	 */
//	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "users_roles", joinColumns = { @JoinColumn(name = "username") }, inverseJoinColumns = {
			@JoinColumn(name = "rolename") })
	private Collection<Role> roles;

	@Past(message = "Invalid birth date")
	@Temporal(TemporalType.DATE)
	@Column(name = "birthdate", nullable = false, insertable = true, updatable = true)
	private Date birthdate;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnore
	private Collection<Post> posts;

	// Convenient method to add a new post to the current user
	public void addPost(Post post) {
		// Check if collection is null
		if (this.posts == null) {
			this.posts = new ArrayList<>();
		}
		// Add the passed post
		this.posts.add(post);
	}

}
