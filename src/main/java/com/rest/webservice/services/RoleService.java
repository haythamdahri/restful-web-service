package com.rest.webservice.services;

import java.util.Collection;

import com.rest.webservice.entities.Role;
import com.rest.webservice.entities.RoleType;
import com.rest.webservice.exceptions.RoleNotFoundException;

/**
 * @author HAYTHAM DAHRI
 * Role service specification 
 */
public interface RoleService {

	/**
	 * Persist the passed role in the database 
	 */
	public Role saveRole(Role role) throws Exception;
	
	/**
	 * Fetch a role from database 
	 * @param id
	 */
	public Role getRole(RoleType roleType) throws RoleNotFoundException;
	
	/**
	 * Delete a role from database 
	 * @param id
	 */
	public Boolean deleteRole(RoleType roleType);
	
	/**
	 * Fetch all roles from database 
	 */
	public Collection<Role> getRoles();
	
}
