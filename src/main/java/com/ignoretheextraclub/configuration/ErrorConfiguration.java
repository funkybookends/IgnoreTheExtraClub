package com.ignoretheextraclub.configuration;

import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by caspar on 30/04/17.
 */
@Configuration
public class ErrorConfiguration
{
    @Bean
    @ConfigurationProperties(prefix = "error")
    public ErrorProperties errorProperties()
    {
        return new ErrorProperties();
    }
}
