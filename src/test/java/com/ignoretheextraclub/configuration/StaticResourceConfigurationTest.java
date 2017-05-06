package com.ignoretheextraclub.configuration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by caspar on 06/05/17.
 */

/**
 * Spring auto configures resources to be available from src/main/resources/{public,static} and some others
 *
 * The confusion is over the distinction between allowing urls and providing paths for resources.
 * The spring security config needs to permit "/{css,image,js}/**" and the the files need to be in
 * src/main/resources/{static,public}/{css,image,js}/file.css, and then just request localhost:8080/css/file.css
 *
 * http://stackoverflow.com/questions/24916894/serving-static-web-resources-in-spring-boot-spring-security-application
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
public class StaticResourceConfigurationTest
{
    private @Autowired MockMvc mvc;

    @Test
    public void EXPECT_canAccessCssDir() throws Exception
    {
        mvc.perform(get("/css/txt.txt"))
                .andExpect(status().isOk())
                .andExpect(content().string("testtext"));
    }

    @Test
    public void EXPECT_canAccessImgDir() throws Exception
    {
        mvc.perform(get("/img/image.txt"))
                .andExpect(status().isOk())
                .andExpect(content().string("testimage"));
    }

    @Test
    public void EXPECT_canAccessJsDir() throws Exception
    {
        mvc.perform(get("/js/js.txt"))
                .andExpect(status().isOk())
                .andExpect(content().string("testjs"));
    }
}
