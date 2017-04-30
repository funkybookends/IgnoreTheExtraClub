package com.ignoretheextraclub.exceptions;

/**
 * Created by caspar on 30/04/17.
 */
public class JuggledNotFoundException extends Exception
{
    public JuggledNotFoundException()
    {
    }

    public JuggledNotFoundException(final String message)
    {
        super(message);
    }

    public JuggledNotFoundException(final String message, final Throwable cause)
    {
        super(message, cause);
    }

    public JuggledNotFoundException(final Throwable cause)
    {
        super(cause);
    }

    public JuggledNotFoundException(final String message, final Throwable cause, final boolean enableSuppression,
                                    final boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
