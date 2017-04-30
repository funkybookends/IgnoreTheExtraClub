package com.ignoretheextraclub.service.user;

import com.ignoretheextraclub.exceptions.UserListNotFoundException;
import com.ignoretheextraclub.exceptions.UsernameTakenException;
import com.ignoretheextraclub.model.juggling.Pattern;
import com.ignoretheextraclub.model.request.RegistrationRequest;
import com.ignoretheextraclub.model.user.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Created by caspar on 05/03/17.
 */
public interface UsersService
{
    /**
     * Returns a user if a user is found, other wise an empty optional.
     *
     * @param username
     *
     * @return
     */
    Optional<User> getUser(String username);

    /**
     * Attempts to register a user. Use {@link #usernameAvailable(String)}} to
     * determine if the username is available.
     *
     * @param registrationRequest
     *
     * @return The registered user.
     *
     * @throws UsernameTakenException if the username is not available
     */
    @Transactional
    User register(RegistrationRequest registrationRequest) throws UsernameTakenException;

    /**
     * Returns if the user name is present. This method can be used to determine if a user exists.
     *
     * @param username
     *
     * @return true if available, false if not
     */
    boolean usernameAvailable(String username);

    /**
     * Adds a pattern to a list. Creates the list if one does not exist.
     *
     * @param user
     * @param pattern
     * @param listName
     *
     * @return if successful
     */
    User addPatternToList(final User user, final Pattern pattern, final String listName);

    User removePatternFromList(final User user, final Pattern pattern, final String listName) throws UserListNotFoundException;
}
