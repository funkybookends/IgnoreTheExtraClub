package com.ignoretheextraclub.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by caspar on 11/02/17.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Unknown Siteswap Type")
public class UnknownSiteswapTypeException extends RuntimeException
{
    public UnknownSiteswapTypeException()
    {
    }

    public UnknownSiteswapTypeException(String message)
    {
        super(message);
    }

    public UnknownSiteswapTypeException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public UnknownSiteswapTypeException(Throwable cause)
    {
        super(cause);
    }

    public UnknownSiteswapTypeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
