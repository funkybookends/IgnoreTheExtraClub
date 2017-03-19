package com.ignoretheextraclub.services;

import com.ignoretheextraclub.exceptions.UsernameTakenException;
import com.ignoretheextraclub.model.data.RegistrationRequest;
import com.ignoretheextraclub.model.data.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Created by caspar on 05/03/17.
 */
public interface UsersService
{
    Optional<User> getUser(String username);

    @Transactional
    User register(RegistrationRequest registrationRequest) throws UsernameTakenException;

    boolean usernameAvailable(String username);
}
