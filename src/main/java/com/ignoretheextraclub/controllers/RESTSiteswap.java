package com.ignoretheextraclub.controllers;

import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.FourHandedSiteswap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by caspar on 14/01/17.
 */
@RestController
public class RESTSiteswap
{
    private static final Logger LOG = LoggerFactory.getLogger(RESTSiteswap.class);

    @RequestMapping(path = "rest/v1/p/fhs/{siteswap}", method = RequestMethod.GET)
    public FourHandedSiteswap fhsController(@PathVariable(value = "siteswap") final String siteswap) throws InvalidSiteswapException
    {
        LOG.info("request for: {}", siteswap);
        final FourHandedSiteswap fourHandedSiteswap = FourHandedSiteswap.create(siteswap);
        return fourHandedSiteswap;
    }

}
