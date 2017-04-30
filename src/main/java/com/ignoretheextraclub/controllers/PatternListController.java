package com.ignoretheextraclub.controllers;

import com.ignoretheextraclub.exceptions.UnknownPatternException;
import com.ignoretheextraclub.exceptions.UserListNotFoundException;
import com.ignoretheextraclub.model.juggling.Pattern;
import com.ignoretheextraclub.model.user.User;
import com.ignoretheextraclub.service.pattern.PatternService;
import com.ignoretheextraclub.service.user.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by caspar on 15/04/17.
 */
@Controller
public class PatternListController
{
    private static final Logger LOG = LoggerFactory.getLogger(PatternListController.class);

    public static final String PATTERN_ID = "patternid";
    public static final String LIST_NAME = "listname";

    private final UsersService usersService;
    private final PatternService patternService;

    @Autowired
    public PatternListController(UsersService usersService,
                                 PatternService patternService)
    {
        this.usersService = usersService;
        this.patternService = patternService;
    }

    @RequestMapping(
            path = "/lists/add/{" + PATTERN_ID + "}/to/{" + LIST_NAME + "}",
            method = RequestMethod.GET
    )
    public ResponseEntity<?> addPatternToList(final @PathVariable(PATTERN_ID) String patternId,
                                              final @PathVariable(LIST_NAME) String listName,
                                              final @AuthenticationPrincipal User user) throws UserListNotFoundException, UnknownPatternException
    {
        LOG.debug("Attempting to ADD {} to {}'s list {}", patternId, user, listName);

        final Pattern pattern = patternService.getById(patternId)
                                              .orElseThrow(() -> new UnknownPatternException(patternId));

        usersService.addPatternToList(user, pattern, listName);

        return ResponseEntity.ok().build();
    }

    @RequestMapping(
            path = "/lists/remove/{" + PATTERN_ID + "}/from/{" + LIST_NAME + "}",
            method = RequestMethod.GET
    )
    public ResponseEntity<?> removePatternFromList(final @PathVariable(PATTERN_ID) String patternId,
                                                   final @PathVariable(LIST_NAME) String listName,
                                                   final @AuthenticationPrincipal User user) throws UserListNotFoundException, UnknownPatternException
    {
        LOG.debug("Attempting to REMOVE {} to {}'s list {}", patternId, user, listName);

        final Pattern pattern = patternService.getById(patternId)
                                              .orElseThrow(() -> new UnknownPatternException(patternId));

        usersService.removePatternFromList(user, pattern, listName);

        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(UserListNotFoundException.class)
    public ResponseEntity<?> handleUnknownList()
    {
        LOG.info("Could not find list");
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(UnknownPatternException.class)
    public ResponseEntity<?> handleUnknownPatternId()
    {
        LOG.info("Could not find pattern");
        return ResponseEntity.badRequest().build();
    }
}
