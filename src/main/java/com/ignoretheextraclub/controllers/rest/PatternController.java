package com.ignoretheextraclub.controllers.rest;

import com.ignoretheextraclub.model.Pattern;
import com.ignoretheextraclub.services.PatternService;
import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by caspar on 11/03/17.
 */
@RestController()
public class PatternController
{
    private static final String NAME = "name";
    private @Autowired PatternService patternService;

    @PreAuthorize("hasRole('USER')")
    @RequestMapping("/p/{" + NAME + "}")
    public Pattern getPattern(@PathVariable(NAME) final String requestName) throws InvalidSiteswapException
    {
        return patternService.getOrCreate(requestName);
    }
}
