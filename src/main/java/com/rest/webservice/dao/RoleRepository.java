package com.rest.webservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rest.webservice.entities.Role;
import com.rest.webservice.entities.RoleType;

@Repository
public interface RoleRepository extends JpaRepository<Role, RoleType>{

}
