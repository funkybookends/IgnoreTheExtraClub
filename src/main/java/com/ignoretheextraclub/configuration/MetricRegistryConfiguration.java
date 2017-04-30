package com.ignoretheextraclub.configuration;

import com.codahale.metrics.MetricRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by caspar on 03/04/17.
 */
@Configuration
public class MetricRegistryConfiguration
{
    @Bean
    public MetricRegistry metricRegistry()
    {
        return new MetricRegistry();
    }
}
