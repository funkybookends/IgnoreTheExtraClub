package com.ignoretheextraclub.itec.pattern;

import java.util.Locale;

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
		final String siteswap = "975";

		final Pattern expected = Pattern.builder()
			.title(siteswap)
			.type("Four Handed Siteswap")
			.siteswap(siteswap)
			.grounded(true)
			.prime(true)
			.numObjects(7)
			.numHands(4)
			.numJugglers(2)
			.name(siteswap)
			.shortDescription(Locale.ENGLISH, "975 is a period 3 pattern with 7 clubs for 2 jugglers. Aidan begins with Double, Zap, Pass, and Becky responds with Pass, Double, Zap.")
			.longDescription(Locale.ENGLISH, "975 is a period 3 pattern with 7 clubs for 2 jugglers. Aidan has 2 clubs in their right hand and 2 clubs in the left hand. Aidan begins with the right hand and his sequence is Double, Zap, Pass. Becky has 2 clubs in her right hand, one club in the left hand and begins half a beat later. Becky begins with the right hand and her sequence is Pass, Double, Zap.")
			.causalDiagramSvg("/p/Four Handed Siteswap/975/causal-diagram")
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
			.type("Two Handed Vanilla Siteswap")
			.siteswap("5")
			.grounded(true)
			.prime(true)
			.numObjects(5)
			.numHands(2)
			.numJugglers(1)
			.name("5")
			.causalDiagramSvg("/p/Two Handed Siteswap/5/causal-diagram")
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
			.siteswap(siteswap)
			.grounded(true)
			.prime(false)
			.numObjects(6)
			.numHands(4)
			.numJugglers(2)
			.name(siteswap)
			.causalDiagramSvg("/p/Synchronous Passing Siteswap/" + siteswap + "/causal-diagram")
			.shortDescription(Locale.ENGLISH, "<3p|3p><3|3> is a period 2 pattern with 6 clubs for 2 jugglers.")
			.longDescription(Locale.ENGLISH, "<3p|3p><3|3> is a period 2 pattern with 6 clubs for 2 jugglers. Aidan has 2 clubs in their right hand and one club in their left hand, starts in their right hand and their sequence is Pass, Self. Bob has 2 clubs in their right hand and one club in their left hand, starts in their right hand and their sequence is Pass, Self.")
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