package com.ignoretheextraclub.itec.pattern;

import com.ignoretheextraclub.itec.ui.Pattern;
import com.ignoretheextraclub.itec.ui.PatternRequest;
import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;

public interface PatternService
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
	Pattern getPattern(PatternRequest patternRequest) throws InvalidSiteswapException;
}
