package com.ignoretheextraclub.exceptions;

/**
 * Created by caspar on 23/04/17.
 */
public class UserListNotFoundException extends Exception
{
    public UserListNotFoundException()
    {
    }

    public UserListNotFoundException(final String listName)
    {
        super("Could not find list [" + listName + "] for that user.");
    }

    public UserListNotFoundException(final String message,
                                     final Throwable cause)
    {
        super(message, cause);
    }

    public UserListNotFoundException(Throwable cause)
    {
        super(cause);
    }

    public UserListNotFoundException(final String message,
                                     final Throwable cause,
                                     final boolean enableSuppression,
                                     final boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
