package com.ignoretheextraclub.itec;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ignoretheextraclub.itec.configuration.ObjectMapperConfiguration;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

public final class ResultMatcher
{
	private static final ObjectMapper OBJECT_MAPPER = ObjectMapperConfiguration.getObjectMapper();

	public static <T> org.springframework.test.web.servlet.ResultMatcher contentMatches(final T object) throws JsonProcessingException
	{
		final String json = OBJECT_MAPPER.writeValueAsString(object);
		return content().json(json);
	}
}
