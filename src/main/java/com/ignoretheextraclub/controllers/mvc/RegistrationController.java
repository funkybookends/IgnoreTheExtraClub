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

/**
 * Created by caspar on 11/03/17.
 */
@Controller
public class RegistrationController
{
    private @Autowired UsersService usersService;

    @PostMapping("/register")
    public String register(final @Valid RegistrationRequest registrationRequest,
                           final BindingResult bindingResult,
                           final Model model)
    {
        if (!registrationRequest.getPassword().equals(registrationRequest.getMatchingPassword()))
        {
            bindingResult.addError(new ObjectError("rawMatchingPassword", "Passwords do not match."));
        }
        if (!bindingResult.hasErrors())
        {
            try
            {
                usersService.register(registrationRequest);
                return "home";
            }
            catch (final UsernameTakenException usernameTakenException)
            {
                bindingResult.addError(new ObjectError("username", usernameTakenException.getMessage()));
            }
        }
        return "register";
    }

    @GetMapping("/register")
    public String register(final RegistrationRequest registrationRequest)
    {
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
