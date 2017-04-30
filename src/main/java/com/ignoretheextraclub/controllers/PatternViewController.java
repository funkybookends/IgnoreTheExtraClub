package com.ignoretheextraclub.controllers;

import com.codahale.metrics.MetricRegistry;
import com.ignoretheextraclub.exceptions.UnknownPatternException;
import com.ignoretheextraclub.model.juggling.Pattern;
import com.ignoretheextraclub.service.pattern.CommentService;
import com.ignoretheextraclub.service.pattern.PatternService;
import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Optional;
import java.util.function.Consumer;

import static com.ignoretheextraclub.configuration.MetricsConfiguration.PATTERN;
import static com.ignoretheextraclub.configuration.MetricsConfiguration.VIEW;
import static com.ignoretheextraclub.configuration.MetricsConfiguration.VIEW_JSON;

/**
 * Created by caspar on 12/03/17.
 */
@Controller
public class PatternViewController
{
    private static final Logger LOG = LoggerFactory.getLogger(PatternViewController.class);

    private static final String NAME = "name";

    public static final String ITEM = "item";
    public static final String PATTERN_SHORT_PREFIX = "p";
    public static final String DETAILSPAGE = "detailspage";

    private final PatternService patternService;

    private Consumer<String> pageViewCounter = (name) -> {};

    @Autowired
    public PatternViewController(final PatternService patternService)
    {
        this.patternService = patternService;
    }

    @Autowired
    public void configureMetrics(final MetricRegistry metricRegistry)
    {
        this.pageViewCounter = (name) -> metricRegistry.counter(MetricRegistry.name(PATTERN, VIEW, name)).inc();
    }

    @RequestMapping(
            path = "/" + PATTERN_SHORT_PREFIX + "/{" + NAME + "}",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE
    )
    public String viewPattern(final @PathVariable(NAME) String requestName,
                              final Model model) throws InvalidSiteswapException
    {
        pageViewCounter.accept(requestName);

        final Pattern pattern = patternService.getOrCreate(requestName);

        model.addAttribute(ITEM, pattern);
        model.addAttribute(HomePageController.SIDEBAR_NEWEST_PATTERNS,
                patternService.newest(0));

        return DETAILSPAGE;
    }

    @RequestMapping(
            path = "/" + PATTERN_SHORT_PREFIX + "/{" + NAME + "}",
            method = RequestMethod.GET,
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_FORM_URLENCODED_VALUE,
                    MediaType.APPLICATION_ATOM_XML_VALUE
            }
    )
    public Pattern getPattern(final @PathVariable(NAME) String requestName) throws InvalidSiteswapException, UnknownPatternException
    {
        pageViewCounter.accept(requestName);

        return patternService.get(requestName)
                .orElseThrow(() -> new UnknownPatternException(requestName));
    }

    @ExceptionHandler(UnknownPatternException.class)
    public ResponseEntity<?> handleUnknownPattern()
    {
        LOG.info("Unknown pattern");
        return ResponseEntity.badRequest().build();
    }
}
