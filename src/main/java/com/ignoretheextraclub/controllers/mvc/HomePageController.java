package com.ignoretheextraclub.controllers.mvc;

import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import com.ignoretheextraclub.model.data.User;
import com.ignoretheextraclub.service.pattern.PatternService;
import com.ignoretheextraclub.service.activity.ActivityService;
import com.ignoretheextraclub.service.user.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * Created by caspar on 19/03/17.
 */
@Controller
public class HomePageController
{
    private static final Logger LOG = LoggerFactory.getLogger(HomePageController.class);

    public static final String SIDEBAR_NEWEST_PATTERNS = "newestPatterns";
    private static final String POSTS = "posts";

    private final PatternService patternService;
    private final ActivityService activityService;
    private final UsersService   usersService;

    private final Meter          HOME_PAGE;
    private final Meter HELLO_PAGE;

    @Autowired
    public HomePageController(final PatternService patternService,
            final UsersService usersService,
            final ActivityService activityService,
            final MetricRegistry metricRegistry)
    {
        this.patternService = patternService;
        this.usersService = usersService;
        this.activityService = activityService;

        this.HOME_PAGE = metricRegistry.meter(MetricRegistry.name("page-views", "/"));
        this.HELLO_PAGE = metricRegistry.meter(MetricRegistry.name("page-views", "/hello"));
    }

    @GetMapping(value = "/")
    public String homePage(final Model model,
                           final @AuthenticationPrincipal User user)
    {
        HOME_PAGE.mark();

        model.addAttribute(POSTS, activityService.newest(0));

        model.addAttribute(SIDEBAR_NEWEST_PATTERNS, patternService.newest(0));

        return "home";
    }

    @GetMapping(value = "/hello")
    public String hello(final Model model,
                        final @AuthenticationPrincipal User user)
    {
        HELLO_PAGE.mark();

        LOG.info("User: " + user.toString());
        model.addAttribute("user", user);
        return "hello";
    }
}
