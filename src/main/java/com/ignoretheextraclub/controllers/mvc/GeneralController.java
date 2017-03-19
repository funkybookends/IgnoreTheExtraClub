package com.ignoretheextraclub.controllers.mvc;

import com.ignoretheextraclub.model.data.User;
import com.ignoretheextraclub.services.PatternService;
import com.ignoretheextraclub.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.logging.Logger;

/**
 * Created by caspar on 19/03/17.
 */
@Controller
public class GeneralController
{
    private static final Logger LOG = Logger.getLogger(GeneralController.class.getCanonicalName());
    public static final String SIDEBAR_NEWEST = "newestPatterns";

    private @Autowired PatternService patternService;
    private @Autowired UsersService   usersService;

    @GetMapping(value = "/")
    public String homePage(final Model model,
                           final @AuthenticationPrincipal User user)
    {
        model.addAttribute(SIDEBAR_NEWEST, patternService.newest(0));

        return "home";
    }

    @GetMapping(value = "/hello")
    public String hello(final Model model,
                        final @AuthenticationPrincipal User user)
    {
        LOG.info("User: " + user.toString());
        model.addAttribute("user", user);
        return "hello";
    }
}
