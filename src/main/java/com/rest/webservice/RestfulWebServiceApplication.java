package com.rest.webservice;

import java.util.Arrays;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.rest.webservice.entities.Role;
import com.rest.webservice.entities.RoleType;
import com.rest.webservice.entities.User;
import com.rest.webservice.services.RoleService;
import com.rest.webservice.services.UserService;

@SpringBootApplication
public class RestfulWebServiceApplication implements CommandLineRunner {

	@Autowired
	private UserService userService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private RoleService roleService;

	public static void main(String[] args) {
		SpringApplication.run(RestfulWebServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		if (this.userService.getUsers().isEmpty()) {
			// Persist roles
			Role user = this.roleService.saveRole(new Role(RoleType.USER, null));
			Role manager = this.roleService.saveRole(new Role(RoleType.MANAGER, null));
			Role admin = this.roleService.saveRole(new Role(RoleType.ADMIN, null));
			// Add two test users
			User user1 = this.userService.saveUser(new User(1L, "haythamdahri", "haythamdahri", this.bCryptPasswordEncoder.encode("toortoor"),
					Arrays.asList(user, manager, admin), new Date(), null));
			User user2 = this.userService.saveUser(new User(2L, "imranedahri", "imranedahri", this.bCryptPasswordEncoder.encode("toortoor"),
					Arrays.asList(manager), new Date(), null));
		}
	}

}
