package com.ignoretheextraclub.itec.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

@Configuration
public class ObjectMapperConfiguration
{
	@Bean
	public ObjectMapper objectMapper()
	{
		return getObjectMapper();
	}

	public static ObjectMapper getObjectMapper()
	{
		return new ObjectMapper()
			.registerModule(new ParameterNamesModule())
			.setSerializationInclusion(JsonInclude.Include.ALWAYS)
			.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
			.configure(SerializationFeature.INDENT_OUTPUT, true);
	}
}
