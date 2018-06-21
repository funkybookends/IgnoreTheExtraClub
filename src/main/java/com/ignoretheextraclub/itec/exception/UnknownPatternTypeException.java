package com.ignoretheextraclub.itec.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Unknown siteswap type")
public class UnknownPatternTypeException extends RuntimeException
{

}
