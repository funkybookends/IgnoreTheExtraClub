package com.ignoretheextraclub.itec.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_IMPLEMENTED, reason = "Cannot produce a causal diagram for this type (yet)")
public class CausalDiagramNotAvailableException extends Exception
{
}
