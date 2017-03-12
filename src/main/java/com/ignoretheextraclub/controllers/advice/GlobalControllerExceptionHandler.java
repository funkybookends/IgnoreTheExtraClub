package com.ignoretheextraclub.controllers.advice;

import com.ignoretheextraclub.exceptions.UnknownPatternException;
import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by caspar on 12/03/17.
 */
@ControllerAdvice
public class GlobalControllerExceptionHandler
{
    @ResponseStatus(value = HttpStatus.EXPECTATION_FAILED, reason =
            "Could not find a pattern with that name or construct a siteswap with it.")
    @ExceptionHandler(InvalidSiteswapException.class)
    public void handleInvalidSiteswapException()
    {
        // Nothing to do
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason =
            "Invalid siteswap or unknown name.")
    @ExceptionHandler(UnknownPatternException.class)
    public void handlePattern()
    {
        // Nothing to do
    }


}
