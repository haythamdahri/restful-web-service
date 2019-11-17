package com.rest.webservice.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
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
		auth.inMemoryAuthentication().withUser("haytham").password(this.bCryptPasswordEncoder.encode("toortoor")).roles("USER", "MANAGER", "ADMIN")
				.and().withUser("imrane").password(this.bCryptPasswordEncoder.encode("toortoor")).roles("MANAGER");
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
		// Enable basic security
		http.httpBasic().and().authorizeRequests().anyRequest().authenticated().and().csrf().disable()
				.headers().frameOptions().disable();
	}

}
