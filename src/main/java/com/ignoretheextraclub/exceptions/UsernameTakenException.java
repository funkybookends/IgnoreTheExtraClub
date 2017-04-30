package com.ignoretheextraclub.exceptions;

/**
 * Created by caspar on 05/03/17.
 */

/**
 * Indicates that a username is taken.
 */
public class UsernameTakenException extends Exception
{
    public static final String DEFAULT_MESSAGE = "Sorry, that username is not available";

    public UsernameTakenException()
    {
        super(DEFAULT_MESSAGE);
    }

    public UsernameTakenException(final String username)
    {
        super("The username [" + username + "] is not available");
    }

    public UsernameTakenException(final String message,
                                  final Throwable cause)
    {
        super(message, cause);
    }

    public UsernameTakenException(Throwable cause)
    {
        super(cause);
    }

    public UsernameTakenException(final String message,
                                  final Throwable cause,
                                  final boolean enableSuppression,
                                  final boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
