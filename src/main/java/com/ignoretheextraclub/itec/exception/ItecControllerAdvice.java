package com.ignoretheextraclub.itec.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;

@ControllerAdvice
public class ItecControllerAdvice
{
	@ExceptionHandler(InvalidSiteswapException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid siteswap")
	public void invalidSiteswapExceptionMessage()
	{
	}
}
