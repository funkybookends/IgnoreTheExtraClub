package com.ignoretheextraclub.service.user.impl;

import com.codahale.metrics.MetricRegistry;
import com.ignoretheextraclub.exceptions.UserListNotFoundException;
import com.ignoretheextraclub.exceptions.UsernameTakenException;
import com.ignoretheextraclub.model.juggling.Pattern;
import com.ignoretheextraclub.model.request.RegistrationRequest;
import com.ignoretheextraclub.model.user.PatternList;
import com.ignoretheextraclub.model.user.PatternListItem;
import com.ignoretheextraclub.model.user.User;
import com.ignoretheextraclub.persistence.UsersRepository;
import com.ignoretheextraclub.service.pattern.CommentService;
import com.ignoretheextraclub.service.user.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
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
    private final CommentService commentService;
    private final MetricRegistry metricRegistry;

    @Autowired
    public UsersServiceImpl(final UsersRepository usersRepository,
                            final PasswordEncoder passwordEncoder,
                            final CommentService commentService,
                            final MetricRegistry metricRegistry)
    {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.commentService = commentService;
        this.metricRegistry = metricRegistry;
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException
    {
        return getUser(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username [" + username + "] not found"));
    }

    @Override
    public Optional<User> getUser(String username)
    {
        final Optional<User> userByUsername = usersRepository.findUserByUsername(username);

        metricRegistry.meter(MetricRegistry.name(USER, FIND, userByUsername.isPresent() ? SUCCESS : FAILURE, username)).mark();

        userByUsername.ifPresent(user -> user.setComments(commentService.getUsersComments(user)));

        return userByUsername;
    }

    @Transactional
    @Override
    public User register(final RegistrationRequest signUp) throws UsernameTakenException
    {
        Optional<User> user = getUser(signUp.getUsername());
        if (user.isPresent())
        {
            throw new UsernameTakenException(signUp.getUsername());
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
    public boolean usernameAvailable(String username)
    {
        return !usersRepository.exists(username);
    }

    @Override
    public User addPatternToList(final User user, final Pattern pattern, final String listName)
    {
        final PatternList patternList = user.getPatternLists()
                                            .getOrDefault(listName, new PatternList(listName, false, new ArrayList<>(), Instant.now(),Instant.now()));

        if (patternList.containsPatternId(pattern.getId()))
        {
            return user; // Pattern list already contains pattern
        }

        final PatternListItem patternListItem = new PatternListItem(pattern.getId(), pattern.getPageTitle(), Instant.now());

        patternList.addPatternListItem(patternListItem);

        return usersRepository.save(user);
    }

    @Override
    public User removePatternFromList(final User user, final Pattern pattern, final String listName) throws UserListNotFoundException
    {
        final PatternList patternList = Optional.ofNullable(user.getPatternLists().get(listName))
                                                .orElseThrow(() -> new UserListNotFoundException(listName));

        return patternList.removePatternById(pattern.getId())
               ? usersRepository.save(user)
               : user;
    }
}
