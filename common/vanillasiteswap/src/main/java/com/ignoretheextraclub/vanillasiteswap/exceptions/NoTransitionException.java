package com.ignoretheextraclub.vanillasiteswap.exceptions;

/**
 * Created by caspar on 26/11/16.
 */
public class NoTransitionException extends Exception
{
    public NoTransitionException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public NoTransitionException(String message)
    {
        super(message);
    }
}
