package com.ignoretheextraclub.controllers;

import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import com.ignoretheextraclub.model.user.User;
import com.ignoretheextraclub.service.activity.ActivityService;
import com.ignoretheextraclub.service.pattern.PatternService;
import com.ignoretheextraclub.service.user.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by caspar on 19/03/17.
 */
@Controller
public class HomePageController
{
    public static final String SIDEBAR_NEWEST_PATTERNS = "newestPatterns";
    private static final Logger LOG = LoggerFactory.getLogger(HomePageController.class);
    private static final String POSTS = "posts";

    private final PatternService patternService;
    private final ActivityService activityService;
    private final UsersService usersService;

    private Meter homePage;
    private Meter helloPage;

    @Autowired
    public HomePageController(final PatternService patternService,
                              final UsersService usersService,
                              final ActivityService activityService)
    {
        this.patternService = patternService;
        this.usersService = usersService;
        this.activityService = activityService;
    }

    @Autowired
    private void configureMeters(final MetricRegistry metricRegistry)
    {
        this.homePage = metricRegistry.meter(MetricRegistry.name("page-views", "/"));
        this.helloPage = metricRegistry.meter(MetricRegistry.name("page-views", "/hello"));
    }
    
    @RequestMapping(
            path = "/",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE
    )
    public String homePage(final Model model,
                           final @AuthenticationPrincipal User user)
    {
        homePage.mark();

        model.addAttribute(POSTS, activityService.newest(0));
        model.addAttribute(SIDEBAR_NEWEST_PATTERNS, patternService.newest(0));

        return "home";
    }

    /**
     * Method for testing // TODO remove
     * @param model
     * @param user
     * @return
     */
    @RequestMapping(
            path = "/hello",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE
    )
    public String hello(final Model model,
                        final @AuthenticationPrincipal User user)
    {
        helloPage.mark();

        model.addAttribute("user", user);

        return "hello";
    }
}
