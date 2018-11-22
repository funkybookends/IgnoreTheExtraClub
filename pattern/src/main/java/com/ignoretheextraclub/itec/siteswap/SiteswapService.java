package com.ignoretheextraclub.itec.siteswap;

import com.ignoretheextraclub.itec.ui.PatternRequest;
import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;

public interface SiteswapService
{
	/**
	 * Finds a specific pattern for the name of any type.
	 *
	 * @param patternRequest The name of the pattern
	 *
	 * @return A pattern
	 *
	 * @throws InvalidSiteswapException If no pattern could be found or created
	 */
	Siteswap getSiteswap(PatternRequest patternRequest) throws InvalidSiteswapException;
}
