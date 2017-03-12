package com.ignoretheextraclub.controllers.mvc;

import com.ignoretheextraclub.model.Pattern;
import com.ignoretheextraclub.services.PatternService;
import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created by caspar on 12/03/17.
 */
@Controller
public class PatternController
{
    private static final String NAME = "name";
    private @Autowired PatternService patternService;

    @GetMapping(value = "/p/{" + NAME + "}")
    public String viewPattern(final @PathVariable(NAME) String requestName,
                              final Model model) throws InvalidSiteswapException
    {
        final Pattern pattern = patternService.getOrCreate(requestName);

        model.addAttribute("pattern", pattern);

        final String type = pattern.getSiteswap()
                                   .getClass()
                                   .getSimpleName();

        return "pattern/" + type;
    }
}
