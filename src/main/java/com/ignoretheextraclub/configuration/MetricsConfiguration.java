package com.ignoretheextraclub.configuration;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.graphite.Graphite;
import com.codahale.metrics.graphite.GraphiteReporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * Created by caspar on 19/03/17.
 */
@Configuration
public class MetricsConfiguration
{
    private static final Logger LOG = LoggerFactory.getLogger(MetricsConfiguration.class);

    @Bean
    public MetricRegistry metricRegistry()
    {
        return new MetricRegistry();
    }

    @Autowired
    private void configureGraphiteReporter(final MetricRegistry metricRegistry)
    {
        // TODO get all these props in configuration file and log them out
        LOG.info("Configuring Graphite.");
        Graphite graphite = new Graphite("localhost", 2003);

        GraphiteReporter reporter = GraphiteReporter.forRegistry(metricRegistry)
                                                    .prefixedWith("IgnoreTheExtraClub")
                                                    .convertDurationsTo(TimeUnit.MILLISECONDS)
                                                    .convertRatesTo(TimeUnit.MILLISECONDS)
                                                    .build(graphite);

        reporter.start(30, TimeUnit.SECONDS);
    }
}
