package com.ignoretheextraclub.controllers;

import com.ignoretheextraclub.exceptions.JuggledNotFoundException;
import com.ignoretheextraclub.exceptions.UnknownPatternException;
import com.ignoretheextraclub.model.juggling.Pattern;
import com.ignoretheextraclub.model.user.Juggled;
import com.ignoretheextraclub.model.user.User;
import com.ignoretheextraclub.service.pattern.PatternService;
import com.ignoretheextraclub.service.user.JuggledService;
import com.ignoretheextraclub.service.user.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.Instant;

/**
 * Created by caspar on 30/04/17.
 */
@Controller
public class JuggledController
{
    private static final Logger LOG = LoggerFactory.getLogger(JuggledController.class);

    private static final String WITH_USERNAME = "with_username";
    private static final String PATTERN_ID = "pattern_id";
    private static final String TIMESTAMP = "timestamp";
    private static final String WHERE = "where";

    private final UsersService usersService;
    private final JuggledService juggledService;
    private final PatternService patternService;

    @Autowired
    public JuggledController(final UsersService usersService,
                             final JuggledService juggledService,
                             final PatternService patternService)
    {
        this.usersService = usersService;
        this.juggledService = juggledService;
        this.patternService = patternService;
    }

    @RequestMapping(
            path = "/juggled/record/{" + PATTERN_ID + "}",
            method = {
                    RequestMethod.PUT,
                    RequestMethod.POST
            },
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public @ResponseBody Juggled recordJuggled(final @PathVariable(value = PATTERN_ID) String patternId,
                                 final @RequestParam(value = WITH_USERNAME, required = false) String withUsername,
                                 final @RequestParam(value = TIMESTAMP, required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) String timestamp,
                                 final @RequestParam(value = WHERE, required = false) String where,
                                 final @AuthenticationPrincipal User user) throws UsernameNotFoundException, UnknownPatternException
    {

        final User withUser = withUsername == null
                              ? null
                              : usersService.getUser(withUsername)
                                            .orElseThrow(() -> new UsernameNotFoundException(
                                                    "Could not find username: [" + withUsername + "]"));

        final Pattern pattern = patternService.getById(patternId)
                                              .orElseThrow(() -> new UnknownPatternException(patternId));

        final Instant when = timestamp == null ? Instant.now() : Instant.parse(timestamp);

        return juggledService.markJuggled(user, pattern, withUser, where, when);
    }

    @RequestMapping(
            path = "/juggled/delete/{juggled_id}",
            method = RequestMethod.DELETE
    )
    public ResponseEntity<?> deleteJuggled(final @PathVariable("juggled_id") String juggledId,
                                           final @AuthenticationPrincipal User user) throws JuggledNotFoundException
    {
        juggledService.deleteJuggled(user, juggledId);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler
    public ResponseEntity<?> handleUnknownPatternException(final UnknownPatternException upe)
    {
        LOG.warn("{}", upe);

        return ResponseEntity.badRequest().build();
    }
}
