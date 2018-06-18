package com.ignoretheextraclub.itec.pattern;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ignoretheextraclub.itec.RestDocsConfiguration;

import static com.ignoretheextraclub.itec.ResultMatcher.contentMatches;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
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
		this.api = RestDocsConfiguration.configure(context, objectMapper, restDocumentation);
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
				.causalDiagramSvg("/p/Four Handed Siteswap/975")
				.build())
			.descriptionDetails(Pattern.DescriptionDetails.builder()
				.build())
			.build();

		api.perform(get("/p/fhs/975"))
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
				.causalDiagramSvg("/p/Two Handed Siteswap/5")
				.build())
			.descriptionDetails(Pattern.DescriptionDetails.builder()
				.build())
			.build();

		api.perform(get("/p/5"))
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
				.causalDiagramSvg("/p/Synchronous Passing Siteswap/" + siteswap)
				.build())
			.descriptionDetails(Pattern.DescriptionDetails.builder()
				.build())
			.build();

		api.perform(get("/p/" + siteswap))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(contentMatches(expected));
	}

	@Test
	public void testInvalidSiteswap() throws Exception
	{
		api.perform(get("/p/fhs/976"))
			.andDo(print())
			.andExpect(status().isBadRequest());
	}

	@Test
	public void testUnknownType() throws Exception
	{
		api.perform(get("/p/unknownType/975"))
			.andDo(print())
			.andExpect(status().isBadRequest());
	}
}