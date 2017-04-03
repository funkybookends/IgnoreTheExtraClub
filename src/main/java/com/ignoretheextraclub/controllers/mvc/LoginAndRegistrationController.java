package com.ignoretheextraclub.controllers.mvc;

import com.codahale.metrics.MetricRegistry;
import com.ignoretheextraclub.exceptions.UsernameTakenException;
import com.ignoretheextraclub.model.data.RegistrationRequest;
import com.ignoretheextraclub.model.data.User;
import com.ignoretheextraclub.service.user.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.BasicErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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
    private final MetricRegistry metricRegistry;

    @Autowired
    public LoginAndRegistrationController(final UsersService usersService,
            final MetricRegistry metricRegistry)
    {
        this.usersService = usersService;
        this.metricRegistry = metricRegistry;
    }

    @PostMapping("/register")
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
                metricRegistry.meter(MetricRegistry.name(REGISTRATION, SUCCESS)).mark();
                model.addAttribute("user", user);
                return "hello";
            }
            catch (final UsernameTakenException usernameTakenException)
            {
                bindingResult.addError(new ObjectError("username", usernameTakenException.getMessage()));
            }
        }

        metricRegistry.meter(MetricRegistry.name(REGISTRATION, FAILURE)).mark();

        return "register";
    }

    @GetMapping("/register")
    public String register(final RegistrationRequest registrationRequest)
    {
        metricRegistry.meter(MetricRegistry.name(REGISTRATION, BEGIN)).mark();
        LOG.info("Starting registration.");
        return "register";
    }

    @GetMapping("/username-available/{username}")
    public ResponseEntity<?> isUserNameAvailable(final @PathVariable("username") String username)
    {
        if (usersService.usernameAvailable(username))
        {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.IM_USED);
        }
    }

    @GetMapping("/login")
    public String login()
    {
        return "login";
    }
}
