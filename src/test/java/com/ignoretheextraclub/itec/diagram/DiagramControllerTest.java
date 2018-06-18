package com.ignoretheextraclub.itec.diagram;

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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DiagramControllerTest
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
	public void test975() throws Exception
	{
		api.perform(get("/p/Four Handed Siteswap/975/causal-diagram")
			.accept(DiagramController.IMAGE_SVG_XML))
			.andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	public void testfhs975() throws Exception
	{
		api.perform(get("/p/fhs/975/causal-diagram")
			.accept(DiagramController.IMAGE_SVG_XML))
			.andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	public void testSixClubTwoCount() throws Exception
	{
		api.perform(get("/p/Synchronous Passing Siteswap/<3p|3p><3|3>/causal-diagram")
			.accept(DiagramController.IMAGE_SVG_XML))
			.andDo(print())
			.andExpect(status().isOk());
	}
}