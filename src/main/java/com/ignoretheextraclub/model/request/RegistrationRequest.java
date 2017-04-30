package com.ignoretheextraclub.model.request;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;

/**
 * Created by caspar on 11/03/17.
 */
public class RegistrationRequest
{
    private static final int MIN_PASSWORD_LENGTH = 8;

    private @NotEmpty String username;
    private @Size(min = MIN_PASSWORD_LENGTH) String password;
    private @Size(min = MIN_PASSWORD_LENGTH) String matchingPassword;

    public RegistrationRequest()
    {
    }

    public static int getMinPasswordLength()
    {
        return MIN_PASSWORD_LENGTH;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(final String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(final String password)
    {
        this.password = password;
    }

    public String getMatchingPassword()
    {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword)
    {
        this.matchingPassword = matchingPassword;
    }
}
