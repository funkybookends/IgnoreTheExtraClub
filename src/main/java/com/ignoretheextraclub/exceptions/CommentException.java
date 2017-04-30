package com.ignoretheextraclub.exceptions;

/**
 * Created by caspar on 15/04/17.
 */
public class CommentException extends Exception
{
    public CommentException()
    {
    }

    public CommentException(final String message)
    {
        super(message);
    }

    public CommentException(final String message,
                            final Throwable cause)
    {
        super(message, cause);
    }

    public CommentException(Throwable cause)
    {
        super(cause);
    }

    public CommentException(final String message,
                            final Throwable cause,
                            final boolean enableSuppression,
                            final boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
