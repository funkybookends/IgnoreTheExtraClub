package com.ignoretheextraclub.exceptions;

/**
 * Created by caspar on 12/03/17.
 */
public class UnknownPatternException extends Throwable
{
    public UnknownPatternException()
    {
    }

    public UnknownPatternException(String message)
    {
        super(message);
    }

    public UnknownPatternException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public UnknownPatternException(Throwable cause)
    {
        super(cause);
    }

    public UnknownPatternException(String message,
                                   Throwable cause,
                                   boolean enableSuppression,
                                   boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
