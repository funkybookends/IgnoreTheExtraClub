package com.ignoretheextraclub.configuration;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.graphite.Graphite;
import com.codahale.metrics.graphite.GraphiteReporter;
import com.ignoretheextraclub.properties.GraphiteProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.concurrent.TimeUnit;

/**
 * Created by caspar on 19/03/17.
 */
@Configuration
@EnableConfigurationProperties(GraphiteProperties.class)
@Import(MetricReigstryConfiguration.class)
public class MetricsConfiguration
{
    private static final Logger LOG = LoggerFactory.getLogger(MetricsConfiguration.class);

    public static final String SUCCESS = "success";
    public static final String FAILURE = "failure";
    public static final String FIND = "find";
    public static final String CREATE = "create";
    public static final String PATTERN = "pattern";
    public static final String VIEW = "view";

    @Autowired
    private void configureGraphiteReporter(final MetricRegistry metricRegistry,
            final GraphiteProperties graphiteProperties)
    {
        if (graphiteProperties.isEnabled())
        {
            Graphite graphite = new Graphite(graphiteProperties.getHostname(),
                    graphiteProperties.getPort());

            GraphiteReporter reporter = GraphiteReporter.forRegistry(metricRegistry)
                    .prefixedWith(graphiteProperties.getPrefix())
                    .convertDurationsTo(TimeUnit.MILLISECONDS)
                    .convertRatesTo(TimeUnit.MILLISECONDS)
                    .build(graphite);

            reporter.start(graphiteProperties.getPeriodSeconds(), TimeUnit.SECONDS);
            LOG.info("Graphite configured and started: {}", graphiteProperties);
        }
        else
        {
            LOG.warn("Graphite is not enabled! {}", graphiteProperties);
        }
    }
}
