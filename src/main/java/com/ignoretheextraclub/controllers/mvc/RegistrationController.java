package com.ignoretheextraclub.controllers.mvc;

import com.ignoretheextraclub.exceptions.UsernameTakenException;
import com.ignoretheextraclub.model.data.RegistrationRequest;
import com.ignoretheextraclub.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.logging.Logger;

/**
 * Created by caspar on 11/03/17.
 */
@Controller
public class RegistrationController
{
    private static final Logger LOG = Logger.getLogger(RegistrationController.class.getCanonicalName());

    private @Autowired UsersService usersService;

    @PostMapping("/register")
    public String register(final @Valid RegistrationRequest registrationRequest,
                           final BindingResult bindingResult,
                           final Model model)
    {
        LOG.info("attempting to register " + registrationRequest.toString());
        if (!registrationRequest.getPassword().equals(registrationRequest.getMatchingPassword()))
        {
            bindingResult.addError(new ObjectError("rawMatchingPassword", "Passwords do not match."));
        }
        if (!bindingResult.hasErrors())
        {
            try
            {
                usersService.register(registrationRequest);
                LOG.info("new user registered");
                return "hello";
            }
            catch (final UsernameTakenException usernameTakenException)
            {
                bindingResult.addError(new ObjectError("username", usernameTakenException.getMessage()));
            }
        }
        LOG.info("could not register user");
        return "register";
    }

    @GetMapping("/register")
    public String register(final RegistrationRequest registrationRequest)
    {
        LOG.info("starting registration");
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
}
