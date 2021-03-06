package com.rest.webservice.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author HAYTHAM DAHRI Security configuration class Enable methods security
 *         Enable secured annotation on methods
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class CustomWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

	/**
	 * Inejct BCryptPasswordEncoder instance
	 */
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	/**
	 * Inejct DataSource instance
	 */
	@Autowired
	private DataSource dataSource;

	/**
	 * Set-up authentication method In memory authentication | Database
	 * authentication
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		/*
		 * Set-up in memory authentication users No need to add ROLE_ prefix for memory
		 * authentication {noop} prefix to specify that the password is stoed in clear
		 * => no operation
		 */
//		auth.inMemoryAuthentication().withUser("haytham").password(this.bCryptPasswordEncoder.encode("toortoor"))
//				.roles("USER", "MANAGER", "ADMIN").and().withUser("imrane")
//				.password(this.bCryptPasswordEncoder.encode("toortoor")).roles("USER", "MANAGER").and().withUser("user")
//				.password(this.bCryptPasswordEncoder.encode("toortoor")).roles("USER");

		// Database authentication
		auth.jdbcAuthentication().dataSource(this.dataSource)
				// Custom query only for H2 database
				.usersByUsernameQuery(
						"select username as principal, password as credentials, true from users where username=?")
				.authoritiesByUsernameQuery(
						"select username as principal, rolename as role from users_roles where username=?")
				.passwordEncoder(this.bCryptPasswordEncoder)
				// When roles are stored directly => ADMIN, USER, MANAGER
				.rolePrefix("ROLE_");
	}

	/**
	 * Set-up web security configuration Ingnore files ...
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
		// Disable security on static resources
		web.ignoring().antMatchers("/css/**", "/js/**", "/images/**");
	}

	/**
	 * Set-up http security Protect resources, authorize resources and configure
	 * different authentication mechanisms
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		/*
		 * Enable basic security Protect /users resource only for admins
		 */

		http.httpBasic().and().authorizeRequests().antMatchers("/h2-console/**").permitAll().antMatchers("/users")
				.hasRole("ADMIN").anyRequest().authenticated().and().csrf().disable().headers().frameOptions().disable()
				// Disable session authentication, require credentials for each request or a JWT
				// token if already configured
				.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

}
