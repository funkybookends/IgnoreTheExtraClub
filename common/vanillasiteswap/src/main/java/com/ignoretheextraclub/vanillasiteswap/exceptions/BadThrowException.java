package com.ignoretheextraclub.vanillasiteswap.exceptions;

/**
 * Created by caspar on 26/11/16.
 */
public class BadThrowException extends Exception
{
    public BadThrowException(String message) {
        super(message);
    }

    public BadThrowException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
