package com.ignoretheextraclub.service.user;

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
    /**
     * Returns a user if a user is found, other wise an empty optional.
     * @param username
     * @return
     */
    Optional<User> getUser(String username);

    /**
     * Attempts to register a user. Use {@link #usernameAvailable(String)}} to
     * determine if the username is available.
     *
     * @param registrationRequest
     * @return The registered user.
     * @throws UsernameTakenException if the username is not available
     */
    @Transactional
    User register(RegistrationRequest registrationRequest) throws UsernameTakenException;

    /**
     * Returns if the user name is present
     * @param username
     * @return true if available, false if not
     */
    boolean usernameAvailable(String username);
}
