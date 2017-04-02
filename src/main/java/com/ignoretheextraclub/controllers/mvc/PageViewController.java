package com.ignoretheextraclub.controllers.mvc;

import com.codahale.metrics.MetricRegistry;
import com.ignoretheextraclub.model.view.PageViewable;
import com.ignoretheextraclub.model.data.Pattern;
import com.ignoretheextraclub.service.pattern.PatternService;
import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static com.ignoretheextraclub.configuration.MetricsConfiguration.PATTERN;
import static com.ignoretheextraclub.configuration.MetricsConfiguration.VIEW;

/**
 * Created by caspar on 12/03/17.
 */
@Controller
public class PageViewController
{
    private static final Logger LOG  = LoggerFactory.getLogger(PageViewController.class);

    private static final String NAME = "name";

    private final PatternService patternService;
    private final MetricRegistry metricRegistry;

    @Autowired
    public PageViewController(final PatternService patternService,
            final MetricRegistry metricRegistry)
    {
        this.patternService = patternService;
        this.metricRegistry = metricRegistry;
    }

    @GetMapping(value = "/p/{" + NAME + "}")
    public String viewPattern(final @PathVariable(NAME) String requestName,
                              final Model model) throws InvalidSiteswapException
    {
        metricRegistry.meter(MetricRegistry.name(PATTERN, VIEW, requestName)).mark();

        final Pattern pattern = patternService.getOrCreate(requestName);

        model.addAttribute(PageViewable.ATTRIBUTE_NAME, pattern);
        model.addAttribute(GeneralController.SIDEBAR_NEWEST, patternService.newest(0));

        return PageViewable.VIEW;
    }
}
