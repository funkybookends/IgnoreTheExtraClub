package com.ignoretheextraclub.service.user.impl;

import com.ignoretheextraclub.exceptions.JuggledNotFoundException;
import com.ignoretheextraclub.model.juggling.Pattern;
import com.ignoretheextraclub.model.user.Juggled;
import com.ignoretheextraclub.model.user.User;
import com.ignoretheextraclub.persistence.UsersRepository;
import com.ignoretheextraclub.service.user.JuggledService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

/**
 * Created by caspar on 30/04/17.
 */
@Service
public class JuggledServiceImpl implements JuggledService
{
    private static final Logger LOG = LoggerFactory.getLogger(JuggledServiceImpl.class);

    private final UsersRepository usersRepository;

    @Autowired
    public JuggledServiceImpl(final UsersRepository usersRepository)
    {
        this.usersRepository = usersRepository;
    }

    @Override public Juggled markJuggled(final User user,
                                         final Pattern pattern,
                                         @javax.annotation.Nullable final User withUser,
                                         final String where,
                                         final Instant when)
    {
        final Juggled juggled = new Juggled(when,
                withUser == null ? null : withUser.getUsername(),
                pattern.getId(),
                where);

        user.addJuggled(juggled);

        usersRepository.save(user);

        return juggled;
    }

    @Override public User deleteJuggled(final User user,
                                        final String juggledId) throws JuggledNotFoundException
    {
        final List<Juggled> juggleds = user.getJuggleds();

        final Juggled juggled = juggleds.stream()
                                         .filter(jugled -> jugled.getId().equals(juggledId))
                                         .findFirst()
                                         .orElseThrow(() -> new JuggledNotFoundException("Cannot find " + juggledId));

        juggleds.remove(juggled);

        return usersRepository.save(user);
    }
}
