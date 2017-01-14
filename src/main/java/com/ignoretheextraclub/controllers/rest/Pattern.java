package com.ignoretheextraclub.controllers.rest;

import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.FourHandedSiteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.TwoHandedSiteswap;
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
@RequestMapping("/rest/v1/p")
public class Pattern
{
    private static final Logger LOG = LoggerFactory.getLogger(Pattern.class);

    @RequestMapping(path = "/fhs/{siteswap}", method = RequestMethod.GET)
    public FourHandedSiteswap fhsController(@PathVariable(value = "siteswap") final String siteswap) throws InvalidSiteswapException
    {
        LOG.info("Request for FHS: {}", siteswap);
        return FourHandedSiteswap.create(siteswap);
    }

    @RequestMapping(path = "/ths/{siteswap}", method = RequestMethod.GET)
    public TwoHandedSiteswap thsController(@PathVariable(value = "siteswap") final String siteswap) throws InvalidSiteswapException
    {
        LOG.info("Request for THS: {}", siteswap);
        return TwoHandedSiteswap.create(siteswap);
    }

}
