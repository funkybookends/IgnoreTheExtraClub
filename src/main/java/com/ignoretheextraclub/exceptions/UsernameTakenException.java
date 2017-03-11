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
        this(DEFAULT_MESSAGE);
    }

    public UsernameTakenException(String message)
    {
        super(message);
    }

    public UsernameTakenException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public UsernameTakenException(Throwable cause)
    {
        super(cause);
    }

    public UsernameTakenException(String message,
                                  Throwable cause,
                                  boolean enableSuppression,
                                  boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
