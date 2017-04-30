package com.ignoretheextraclub.controllers;

import com.codahale.metrics.Counter;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import com.ignoretheextraclub.exceptions.UsernameTakenException;
import com.ignoretheextraclub.model.request.RegistrationRequest;
import com.ignoretheextraclub.model.user.User;
import com.ignoretheextraclub.service.user.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

import static com.ignoretheextraclub.configuration.MetricsConfiguration.FAILURE;
import static com.ignoretheextraclub.configuration.MetricsConfiguration.SUCCESS;

/**
 * Created by caspar on 11/03/17.
 */
@Controller
public class LoginAndRegistrationController
{
    private static final Logger LOG = LoggerFactory.getLogger(LoginAndRegistrationController.class);

    private static final String REGISTRATION = "registration";
    private static final String BEGIN = "begin";

    private final UsersService usersService;

    private Counter registrationSuccess;
    private Meter registrationFailure;
    private Meter registrationBegin;

    @Autowired
    public LoginAndRegistrationController(final UsersService usersService)
    {
        this.usersService = usersService;
    }

    @Autowired
    public void configureMetrics(final MetricRegistry metricRegistry)
    {
        this.registrationSuccess = metricRegistry.counter(MetricRegistry.name(REGISTRATION, SUCCESS));
        this.registrationFailure = metricRegistry.meter(MetricRegistry.name(REGISTRATION, FAILURE));
        this.registrationBegin = metricRegistry.meter(MetricRegistry.name(REGISTRATION, BEGIN));
    }

    @RequestMapping(
            path = "/register",
            method = RequestMethod.POST,
            produces = MediaType.TEXT_HTML_VALUE
    )
    public String register(final @Valid RegistrationRequest registrationRequest,
                           final BindingResult bindingResult,
                           final Model model)
    {
        LOG.info("Registration Request: {}", registrationRequest);

        if (!registrationRequest.getPassword().equals(registrationRequest.getMatchingPassword()))
        {
            bindingResult.addError(new ObjectError("rawMatchingPassword", "Passwords do not match."));
        }

        if (!bindingResult.hasErrors())
        {
            try
            {
                final User user = usersService.register(registrationRequest);
                this.registrationSuccess.inc();
                model.addAttribute("user", user);
                return "hello";
            }
            catch (final UsernameTakenException usernameTakenException)
            {
                bindingResult.addError(new ObjectError("username", usernameTakenException.getMessage()));
            }
        }

        this.registrationFailure.mark();

        return "register";
    }

    /**
     * Required to inform the Thymeleaf that it needs to populate a registration request.
     *
     * @param registrationRequest
     * @return
     */
    @RequestMapping(
            path = "/register",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE
    )
    public String register(final RegistrationRequest registrationRequest)
    {
        LOG.info("Starting registration.");
        registrationBegin.mark();
        return "register";
    }

    /**
     * A convenience end point that will return if the user name is available
     * in the response code.
     *
     * @param username The username to check
     *
     * @return {@link HttpStatus#OK} if available, otherwise {@link HttpStatus#IM_USED}
     */
    @RequestMapping(
            path = "/username-available/{username}",
            method = RequestMethod.GET,
            produces = MediaType.ALL_VALUE
    )
    public ResponseEntity<?> isUserNameAvailable(final @PathVariable("username") String username)
    {
        return usersService.usernameAvailable(username)
               ? new ResponseEntity<>(HttpStatus.OK)
               : new ResponseEntity<>(HttpStatus.IM_USED);
    }

    @RequestMapping(
            path = "/login",
            method = RequestMethod.GET
    )
    public String login()
    {
        return "login";
    }
}
