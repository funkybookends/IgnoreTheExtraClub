//package com.ignoretheextraclub.controllers.rest;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.ignoretheextraclub.model.Pattern;
//import com.ignoretheextraclub.model.PatternName;
//import com.ignoretheextraclub.model.PatternRepository;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.mock.web.MockHttpServletResponse;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.ResultActions;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//
//import static com.ignoretheextraclub.model.SiteswapAdapter.FOUR_HANDED_SITESWAP_SHORT;
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.core.Is.is;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//
//import org.junit.Assert;
///**
// * Created by caspar on 14/01/17.
// */
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@EnableWebMvc
//public class GetPatternTest
//{
//    @Autowired
//    private WebApplicationContext context;
//
//    private MockMvc mvc;
//
//    private static final ObjectMapper om = new ObjectMapper();
//
//    @Autowired
//    private PatternRepository patternRepository;
//
//    @Autowired
//    private NameRepository nameRepository;
//
//    @Before
//    public void setUp() throws Exception
//    {
//        mvc = MockMvcBuilders.webAppContextSetup(context).build();
//        patternRepository.deleteAll();
//        nameRepository.deleteAll();
//    }
//
//    @Test
//    public void fhsController() throws Exception
//    {
//        ResultActions perform = mvc.perform(get("/rest/v1/p/4hs/{siteswap}", "975"));
//        MvcResult mvcResult = perform.andReturn();
//        MockHttpServletResponse response = mvcResult.getResponse();
//        System.out.println(response.getContentAsString());
//    }
//
//    @Test
//    public void GIVEN_requestWithUnsortedSiteswap_EXPECT_twoNames() throws Exception
//    {
//        mvc.perform(get("/rest/v1/p/{type}/{siteswap}", FOUR_HANDED_SITESWAP_SHORT, "597"));
//
//        PatternName name975 = nameRepository.findOne(PatternName.getId(FOUR_HANDED_SITESWAP_SHORT, "975"));
//
//        // Check name properties
//        Assert.assertNotNull( "Could not find PatternName(975)", name975);
//        Assert.assertNotNull(name975.getCreatedDate());
//
//        // Get Pattern
//        Pattern p975 = patternRepository.findOne(name975.getPatternId());
//        Assert.assertNotNull(p975);
//
//        assertThat(p975.getNames().size(), is(2));
//
//    }
//}