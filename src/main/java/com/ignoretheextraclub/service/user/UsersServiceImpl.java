package com.ignoretheextraclub.service.user;

import com.codahale.metrics.MetricRegistry;
import com.ignoretheextraclub.exceptions.UsernameTakenException;
import com.ignoretheextraclub.model.data.RegistrationRequest;
import com.ignoretheextraclub.model.data.User;
import com.ignoretheextraclub.persistence.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.ignoretheextraclub.configuration.MetricsConfiguration.CREATE;
import static com.ignoretheextraclub.configuration.MetricsConfiguration.FAILURE;
import static com.ignoretheextraclub.configuration.MetricsConfiguration.FIND;
import static com.ignoretheextraclub.configuration.MetricsConfiguration.SUCCESS;

/**
 * Created by caspar on 05/03/17.
 */
@Service
public class UsersServiceImpl implements UsersService, UserDetailsService
{
    private static final Logger LOG = LoggerFactory.getLogger(UsersServiceImpl.class);

    private static final String USER = "username";

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final MetricRegistry metricRegistry;

    @Autowired
    public UsersServiceImpl(final UsersRepository usersRepository,
            final PasswordEncoder passwordEncoder,
            final MetricRegistry metricRegistry)
    {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.metricRegistry = metricRegistry;
    }

    @Override
    public Optional<User> getUser(String username)
    {
        final Optional<User> userByUsername = usersRepository.findUserByUsername(
                username);

        metricRegistry.meter(MetricRegistry.name(USER, FIND,
                userByUsername.isPresent() ? SUCCESS : FAILURE,
                username))
                .mark();

        return userByUsername;
    }

    @Transactional
    @Override
    public User register(final RegistrationRequest signUp) throws UsernameTakenException
    {
        Optional<User> user = getUser(signUp.getUsername());
        if (user.isPresent())
        {
            throw new UsernameTakenException("Sorry, " + signUp.getUsername() + " is not available.");
        }
        else
        {
            final User newUser = convert(signUp);
            LOG.info("New user: {}", newUser);
            final User savedNewUser = usersRepository.save(newUser);

            metricRegistry.meter(MetricRegistry.name(USER, CREATE)).mark();

            return savedNewUser;
        }
    }

    private User convert(final RegistrationRequest signUp)
    {
        return new User(signUp.getUsername(),
                passwordEncoder.encode(signUp.getPassword()));
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException
    {
        return getUser(username).orElseThrow(() -> new UsernameNotFoundException(
                "Username [" + username + "] not found"));
    }

    @Override
    public boolean usernameAvailable(String username)
    {
        return !usersRepository.exists(username);
    }
}
