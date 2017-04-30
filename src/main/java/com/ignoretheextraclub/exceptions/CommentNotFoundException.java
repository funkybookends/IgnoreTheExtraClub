package com.ignoretheextraclub.exceptions;

/**
 * Created by caspar on 29/04/17.
 */
public class CommentNotFoundException extends Exception
{
    public CommentNotFoundException()
    {
    }

    public CommentNotFoundException(final String commentId)
    {
        super("Could not find comment with commentId [" + commentId + "].");
    }

    public CommentNotFoundException(final String message,
                                   final Throwable cause)
    {
        super(message, cause);
    }

    public CommentNotFoundException(final Throwable cause)
    {
        super(cause);
    }

    public CommentNotFoundException(final String message,
                                   final Throwable cause,
                                   final boolean enableSuppression,
                                   final boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
