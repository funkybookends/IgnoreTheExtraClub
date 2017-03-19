package com.ignoretheextraclub.controllers.mvc;

import com.ignoretheextraclub.services.PatternService;
import com.ignoretheextraclub.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by caspar on 19/03/17.
 */
@Controller
public class GeneralController
{
    public static final String SIDEBAR_NEWEST = "newestPatterns";

    private @Autowired PatternService patternService;

    private @Autowired UsersService usersService;

    @GetMapping(value = "/")
    public String homePage(final Model model)
    {
        model.addAttribute(SIDEBAR_NEWEST, patternService.newest(0));

        return "home";
    }
}
