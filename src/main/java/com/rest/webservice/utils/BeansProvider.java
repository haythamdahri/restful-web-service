package com.rest.webservice.utils;

import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

/**
 * @author HAYTHAM DAHRI
 * Component provider class 
 */
@Component
public class BeansProvider {

	/**
	 * Provide LocaleResolver instance object
	 * Set US as a locale resolver
	 */
	public LocaleResolver getLocalResolver() {
		AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
		localeResolver.setDefaultLocale(Locale.US);
		return localeResolver;
	}
	
	/**
	 * Provide ResourceBundleMessageSource instance object 
	 * Set file basename
	 * "messages" is the basename => messages_fr.properties | messages_ar.properties
	 */
//	public ResourceBundleMessageSource getResourceBundleMessageSource() {
//		ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
//		resourceBundleMessageSource.setBasename("messages");
//		return resourceBundleMessageSource;
//	}
	
	/**
	 * BCryptPasswordEncoder method provider 
	 */
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
