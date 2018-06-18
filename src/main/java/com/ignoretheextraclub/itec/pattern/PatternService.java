package com.ignoretheextraclub.itec.pattern;

import com.ignoretheextraclub.itec.exception.UnknownPatternTypeException;
import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;

public interface PatternService
{
	/**
	 * Finds a specific pattern for the name or constructor with the provided
	 * type.
	 *
	 * @param type The required type of the pattern.
	 * @param name The name or constructor for the pattern
	 *
	 * @return A pattern
	 *
	 * @throws InvalidSiteswapException If no pattern could be found or created
	 *                                  for this name and this type.
	 */
	Pattern getPattern(String type, String name) throws InvalidSiteswapException, UnknownPatternTypeException;

	/**
	 * Finds a specific pattern for the name of any type.
	 *
	 * @param name The name of the pattern
	 *
	 * @return A pattern
	 *
	 * @throws InvalidSiteswapException If no pattern could be found or created
	 */
	Pattern getPattern(String name) throws InvalidSiteswapException;
}
