package com.ignoretheextraclub.itec.siteswap;

import com.ignoretheextraclub.itec.exception.UnknownPatternTypeException;
import com.ignoretheextraclub.itec.siteswap.impl.SiteswapType;
import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;

public interface SiteswapService
{
	/**
	 * Finds a specific siteswap for the name or constructor with the provided
	 * type.
	 *
	 * @param type The required type of the siteswap.
	 * @param name The name or constructor for the siteswap
	 *
	 * @return A pattern
	 *
	 * @throws InvalidSiteswapException If no pattern could be found or created
	 *                                  for this name and this type.
	 */
	Siteswap getSiteswap(SiteswapType type, String name) throws InvalidSiteswapException, UnknownPatternTypeException;

	/**
	 * Finds a specific pattern for the name of any type.
	 *
	 * @param name The name of the pattern
	 *
	 * @return A pattern
	 *
	 * @throws InvalidSiteswapException If no pattern could be found or created
	 */
	Siteswap getSiteswap(String name) throws InvalidSiteswapException;
}
