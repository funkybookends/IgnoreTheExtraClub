package com.ignoretheextraclub.itec;

import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.cli.CliDocumentation;
import org.springframework.restdocs.http.HttpDocumentation;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import capital.scalable.restdocs.AutoDocumentation;
import capital.scalable.restdocs.jackson.JacksonResultHandlers;
import capital.scalable.restdocs.response.ResponseModifyingPreprocessors;

public final class RestDocsConfiguration
{
	public static MockMvc configure(final WebApplicationContext context, final ObjectMapper objectMapper, final JUnitRestDocumentation restDocumentation)
	{
		return MockMvcBuilders
			.webAppContextSetup(context)
			.alwaysDo(JacksonResultHandlers.prepareJackson(objectMapper))
			.alwaysDo(MockMvcRestDocumentation.document("{class-name}/{method-name}",
				Preprocessors.preprocessRequest(),
				Preprocessors.preprocessResponse(
					ResponseModifyingPreprocessors.replaceBinaryContent(),
					ResponseModifyingPreprocessors.limitJsonArrayLength(objectMapper),
					Preprocessors.prettyPrint())))
			.apply(MockMvcRestDocumentation.documentationConfiguration(restDocumentation)
				.uris()
				.withScheme("http")
				.withHost("localhost")
				.withPort(8080)
				.and().snippets()
				.withDefaults(CliDocumentation.curlRequest(),
					HttpDocumentation.httpRequest(),
					HttpDocumentation.httpResponse(),
					AutoDocumentation.requestFields(),
					AutoDocumentation.responseFields(),
					AutoDocumentation.pathParameters(),
					AutoDocumentation.requestParameters(),
					AutoDocumentation.description(),
					AutoDocumentation.methodAndPath(),
					AutoDocumentation.section()))
			.build();
	}
}
