package com.rest.webservice.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rest.webservice.dao.RoleRepository;
import com.rest.webservice.entities.Role;
import com.rest.webservice.entities.RoleType;
import com.rest.webservice.exceptions.PostNotFoundException;
import com.rest.webservice.exceptions.RoleNotFoundException;

/**
 * @author HAYTHAM DAHRI Role service implementation
 */
@Service
public class RoleServiceImpl implements RoleService {

	/**
	 * Inject instance of role dao
	 */
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public Role saveRole(Role role) throws Exception {
		// Save the role and return the new persisted version of it
		return this.roleRepository.save(role);
	}

	@Override
	public Role getRole(RoleType roleType) throws RoleNotFoundException {
		// Return the retrieved role or null if not found
		return this.roleRepository.findById(roleType)
				.orElseThrow(() -> new PostNotFoundException("No role has been found with id: " + roleType));
	}

	@Override
	public Boolean deleteRole(RoleType roleType) {
		// Delete role from database using dao object
		this.roleRepository.deleteById(roleType);
		// Return response
		return true;
	}

	@Override
	public Collection<Role> getRoles() {
		// Return all posts using dao object
		return this.roleRepository.findAll();
	}


}
