package com.ignoretheextraclub.itec.pattern;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.cli.CliDocumentation;
import org.springframework.restdocs.http.HttpDocumentation;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.databind.ObjectMapper;

import capital.scalable.restdocs.AutoDocumentation;
import capital.scalable.restdocs.jackson.JacksonResultHandlers;
import capital.scalable.restdocs.response.ResponseModifyingPreprocessors;

import static com.ignoretheextraclub.itec.ResultMatcher.contentMatches;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PatternControllerTest
{
	@Rule
	public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

	private MockMvc api;

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private ObjectMapper objectMapper;

	@Before
	public void setUp() throws Exception
	{
		this.api = MockMvcBuilders
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

	@Test
	public void testFhs975() throws Exception
	{
		final Pattern expected = Pattern.builder()
			.title("975")
			.type("Four Handed Siteswap")
			.siteswapDetails(Pattern.SiteswapDetails.builder()
				.siteswap("975")
				.grounded(true)
				.prime(true)
				.numObjects(7)
				.numHands(4)
				.numJugglers(2)
				.build())
			.links(Pattern.Links.builder()
				.build())
			.descriptionDetails(Pattern.DescriptionDetails.builder()
				.build())
			.build();

		api.perform(MockMvcRequestBuilders.get("/p/fhs/975"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(contentMatches(expected));
	}

	@Test
	public void test5() throws Exception
	{
		final Pattern expected = Pattern.builder()
			.title("5")
			.type("Two Handed Siteswap")
			.siteswapDetails(Pattern.SiteswapDetails.builder()
				.siteswap("5")
				.grounded(true)
				.prime(true)
				.numObjects(5)
				.numHands(2)
				.numJugglers(1)
				.build())
			.links(Pattern.Links.builder()
				.build())
			.descriptionDetails(Pattern.DescriptionDetails.builder()
				.build())
			.build();

		api.perform(MockMvcRequestBuilders.get("/p/5"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(contentMatches(expected));
	}

	@Test
	public void test6ClubTwoCount() throws Exception
	{
		final String siteswap = "<3p|3p><3|3>";

		final Pattern expected = Pattern.builder()
			.title(siteswap)
			.type("Synchronous Passing Siteswap")
			.siteswapDetails(Pattern.SiteswapDetails.builder()
				.siteswap(siteswap)
				.grounded(true)
				.prime(false)
				.numObjects(6)
				.numHands(4)
				.numJugglers(2)
				.build())
			.links(Pattern.Links.builder()
				.build())
			.descriptionDetails(Pattern.DescriptionDetails.builder()
				.build())
			.build();

		api.perform(MockMvcRequestBuilders.get("/p/" + siteswap))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(contentMatches(expected));
	}

	@Test
	public void testInvalidSiteswap() throws Exception
	{
		api.perform(MockMvcRequestBuilders.get("/p/fhs/976"))
			.andDo(print())
			.andExpect(status().isBadRequest());
	}


	@Test
	public void testUnknownType() throws Exception
	{
		api.perform(MockMvcRequestBuilders.get("/p/unknownType/975"))
			.andDo(print())
			.andExpect(status().isBadRequest());
	}

	private static class Error
	{
		Map<String, Object> additionalProperties = new LinkedHashMap<>();

		@JsonAnySetter
		public void setAdditionalProperty(final String key, final Object value)
		{
			additionalProperties.put(key, value);
		}

		@JsonAnyGetter
		public Map<String, Object> getAdditionalProperties()
		{
			return additionalProperties;
		}

		public String get(final String key)
		{
			return additionalProperties.get(key).toString();
		}
	}
}