package com.ignoretheextraclub.controllers;

import com.codahale.metrics.MetricRegistry;
import com.ignoretheextraclub.model.user.User;
import com.ignoretheextraclub.service.pattern.PatternService;
import com.ignoretheextraclub.service.user.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by caspar on 15/04/17.
 */
public class UsersController
{
    public static final String USERNAME = "username";
    private static final Logger LOG = LoggerFactory.getLogger(UsersController.class);
    private static final String USER = "user";

    private final UsersService usersService;
    private final PatternService patternService;
    private final MetricRegistry metricRegistry;

    @Autowired
    public UsersController(final UsersService usersService,
                           final PatternService patternService,
                           final MetricRegistry metricRegistry)
    {
        this.usersService = usersService;
        this.patternService = patternService;
        this.metricRegistry = metricRegistry;
    }

    @RequestMapping(
            path = "/u/{" + USERNAME + "}",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE
    )
    public String getUserPage(final Model model,
                              final @PathVariable(USERNAME) String username,
                              final @AuthenticationPrincipal User user)
    {
        if (!user.getUsername().equals(username) || !user.isPubliclyVisible())
        {
            return "usernotvisible";
        }

        model.addAttribute(USER, user);

        return user.isPubliclyVisible()
               ? "viewuser"
               : "userpage";
    }

    @RequestMapping(
            path = "/admin/setaccountlocked/{" + USERNAME + "}",
            method = RequestMethod.GET
    )
    public ResponseEntity<?> setAccountLocked(final @PathVariable(USERNAME) String username)
    {
        final User user = usersService.getUser(username)
                                      .orElseThrow(() -> new UsernameNotFoundException("Could not find username: [" + username + "]"));

        user.setAccountLocked(true);

        return ResponseEntity.ok().build();
    }

    @RequestMapping(
            path = "/admin/setdisabled/{" + USERNAME + "}",
            method = RequestMethod.GET
    )
    public ResponseEntity<?> setDisabled(final @PathVariable(USERNAME) String username)
    {
        final User user = usersService.getUser(username)
                                      .orElseThrow(() -> new UsernameNotFoundException("Could not find username: [" + username + "]"));

        user.setAccountExpired(true);

        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> handleUsernameNotFoundException()
    {
        return ResponseEntity.badRequest().build();
    }
}
