package com.rest.webservice.filtering;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
@RequestMapping(path = "/filtering")
public class RestFilteringController {

	@RequestMapping(path = "")
	public MappingJacksonValue myBean() {
		
		// MyBean instance object
		 MyBean myBean = new MyBean("value1", "value2", "value3");
		 
		 // Set fields to be excluded from response
		 SimpleBeanPropertyFilter simpleBeanPropertyFilter = SimpleBeanPropertyFilter.filterOutAllExcept("field3");
		 FilterProvider filterProvider = new SimpleFilterProvider().addFilter("MyBeanFilter", simpleBeanPropertyFilter);
		 
		 MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(myBean);
		 
		 mappingJacksonValue.setFilters(filterProvider);
		 
		 return mappingJacksonValue;
		 
	}
	

	@RequestMapping(path = "list")
	public MappingJacksonValue myBeansList() {
		
		// MyBean instance object
		 List<MyBean> myBeans = Arrays.asList(new MyBean("value1", "value2", "value3"), new MyBean("value1", "value2", "value3"));
		 
		 // Set fields to be excluded from response
		 SimpleBeanPropertyFilter simpleBeanPropertyFilter = SimpleBeanPropertyFilter.filterOutAllExcept("field3");
		 FilterProvider filterProvider = new SimpleFilterProvider().addFilter("MyBeanFilter", simpleBeanPropertyFilter);
		 
		 // Create Mapping Jackson Value object
		 MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(myBeans);
		 
		 // Set the filter to exclude fields from the response
		 mappingJacksonValue.setFilters(filterProvider);
		 
		 // Return the mapping as a response
		 return mappingJacksonValue;
		 
	}
	
}
