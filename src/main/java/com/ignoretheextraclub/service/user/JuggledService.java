package com.ignoretheextraclub.service.user;

import com.ignoretheextraclub.exceptions.JuggledNotFoundException;
import com.ignoretheextraclub.model.juggling.Pattern;
import com.ignoretheextraclub.model.user.Juggled;
import com.ignoretheextraclub.model.user.User;

import javax.annotation.Nullable;
import java.time.Instant;

/**
 * Created by caspar on 14/04/17.
 */
public interface JuggledService
{
    Juggled markJuggled(User user, Pattern pattern, @Nullable User withUser, String where, Instant when);

    User deleteJuggled(User user, String juggledId) throws JuggledNotFoundException;
}
