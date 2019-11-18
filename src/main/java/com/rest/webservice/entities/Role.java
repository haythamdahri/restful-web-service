package com.rest.webservice.entities;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author HAYTHAM DAHRI Role entity class
 */
@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
public class Role {
	
	@Id
	@Enumerated(EnumType.STRING)
	@Column(name = "rolename", nullable = false, insertable = true, updatable = true, unique = true)
	private RoleType roleName;
	
	/**
	 * ManyToMany relationship between roles and users 
	 */
	@ManyToMany(mappedBy="roles")
	private Collection<User> users;
	
}
