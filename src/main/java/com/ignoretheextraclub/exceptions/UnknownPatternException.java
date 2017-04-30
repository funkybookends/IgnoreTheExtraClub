package com.ignoretheextraclub.exceptions;

/**
 * Created by caspar on 12/03/17.
 */
public class UnknownPatternException extends Exception
{
    public UnknownPatternException()
    {
    }

    public UnknownPatternException(final String patternName)
    {
        super("Could not find pattern [" + patternName + "].");
    }

    public UnknownPatternException(final String message,
                                   final Throwable cause)
    {
        super(message, cause);
    }

    public UnknownPatternException(final Throwable cause)
    {
        super(cause);
    }

    public UnknownPatternException(final String message,
                                   final Throwable cause,
                                   final boolean enableSuppression,
                                   final boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
