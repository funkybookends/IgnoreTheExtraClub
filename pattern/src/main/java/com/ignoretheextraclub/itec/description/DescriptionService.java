package com.ignoretheextraclub.itec.description;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import com.ignoretheextraclub.siteswapfactory.describer.Description;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;

public interface DescriptionService
{
	/**
	 * Creates a description for a siteswap
	 *
	 * @param siteswap the siteswap to describe
	 * @param priorityList The locale priority list
	 *
	 * @return The description if available
	 */
	Optional<Description> describe(Siteswap siteswap, List<Locale.LanguageRange> priorityList);

	/**
	 * Describe this siteswap in all available locales
	 *
	 * @param siteswap The siteswap to describe
	 *
	 * @return A map of locale to description
	 */
	Map<Locale, Description> describe(Siteswap siteswap);
}
