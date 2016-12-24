package com.ignoretheextraclub.vanillasiteswap.exceptions;

/**
 * Created by caspar on 26/11/16.
 */
public class InvalidSiteswapException extends Exception
{
    public InvalidSiteswapException(String message) {
        super(message);
    }

    public InvalidSiteswapException(String message, Throwable cause) {
        super(message, cause);
    }
}
