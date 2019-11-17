package com.rest.webservice.filtering;

import com.fasterxml.jackson.annotation.JsonFilter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@JsonIgnoreProperties(value = {"field2", "field3"})
@JsonFilter(value = "MyBeanFilter")
public class MyBean {
	
//	@JsonIgnore
	private String field1;
	
	private String field2;
	
	private String field3;

}
