package com.ignoretheextraclub.controllers.rest;

import com.ignoretheextraclub.exceptions.UnknownPatternException;
import com.ignoretheextraclub.model.Pattern;
import com.ignoretheextraclub.services.PatternService;
import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * Created by caspar on 11/03/17.
 */
@RestController
public class RestPatternController
{
    private static final String NAME = "name";

    private @Autowired PatternService patternService;

    @GetMapping(value = "/rest/p/{" + NAME + "}")
    public Pattern getPattern(final @PathVariable(NAME) String requestName) throws InvalidSiteswapException, UnknownPatternException
    {
        Optional<Pattern> patternOptional = patternService.get(requestName);
        if (patternOptional.isPresent())
        {
            return patternOptional.get();
        }
        else
        {
            throw new UnknownPatternException();
        }
    }
}
