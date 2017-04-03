package com.ignoretheextraclub.controllers.mvc;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by caspar on 02/04/17.
 */
@Controller
public class DefaultErrorController implements ErrorController
{
    public final static String ERROR_PATH = "/error";

    @Override
    public String getErrorPath()
    {
        return ERROR_PATH;
    }

    @ExceptionHandler
    public String errorHandler()
    {
        return "error";
    }

    @GetMapping(path = ERROR_PATH)
    public String renderErrorPage()
    {
        return "error";
    }
}
