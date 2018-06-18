package com.ignoretheextraclub.itec.pattern;

import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;

public interface PatternPopulator
{
	/**
	 * Add information to the Pattern Builder about this siteswap.
	 *
	 * @param builder  The builder to add to
	 * @param siteswap The siteswap to use.
	 */
	void populate(Pattern.PatternBuilder builder, Siteswap siteswap);
}
