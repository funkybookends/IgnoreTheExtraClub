package com.ignoretheextraclub.services.impl;

import com.ignoretheextraclub.exceptions.UsernameTakenException;
import com.ignoretheextraclub.model.data.RegistrationRequest;
import com.ignoretheextraclub.model.data.User;
import com.ignoretheextraclub.persistence.repository.UsersRepository;
import com.ignoretheextraclub.services.UsersService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Created by caspar on 05/03/17.
 */
@Service
public class UsersServiceImpl implements UsersService, UserDetailsService
{
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public UsersServiceImpl(UsersRepository usersRepository,
                            PasswordEncoder passwordEncoder)
    {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<User> getUser(String username)
    {
        return usersRepository.findUserByUsername(username);
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
            return usersRepository.save(convert(signUp));
        }
    }

    private User convert(final RegistrationRequest signUp)
    {
        return new User(signUp.getUsername(), passwordEncoder.encode(signUp.getPassword()));
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
