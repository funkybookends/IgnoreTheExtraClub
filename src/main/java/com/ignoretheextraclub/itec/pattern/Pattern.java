package com.ignoretheextraclub.itec.pattern;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import lombok.Builder;
import lombok.Singular;
import lombok.Value;

/**
 * A DTO for patterns
 */
@Value
@Builder
public class Pattern
{
	/**
	 * The main name for the pattern
	 */
	String title;

	/**
	 * The type of pattern
	 */
	String type;

	/**
	 * Alternative names for the pattern. These could be colloquial, or other
	 * representations of the pattern
	 */
	@Singular List<String> names;

	/**
	 * An object with high level details of the pattern
	 */
	SiteswapDetails siteswapDetails;

	/**
	 * An object with descriptive (human readable) text about the pattern.
	 */
	DescriptionDetails descriptionDetails;

	/**
	 * An object with http links for more on the pattern.
	 */
	Links links;

	@Value
	@Builder
	public static class SiteswapDetails
	{
		/**
		 * The number of jugglers in the pattern.
		 */
		int numJugglers;

		/**
		 * The number of hands.
		 */
		int numHands;

		/**
		 * The number of objects in the pattern.
		 */
		int numObjects;

		/**
		 * True if this pattern does not repeat any state.
		 */
		boolean prime;

		/**
		 * True if this pattern visits the ground state.
		 */
		boolean grounded;

		/**
		 * The canonical siteswap notation for this pattern.
		 */
		String siteswap;
	}

	@Value
	@Builder
	public static class DescriptionDetails
	{
		/**
		 * A map of Locale to a short description max 140 chars.
		 */
		@Singular Map<Locale, String> shortDescriptions;

		/**
		 * A map of Locale to a longer description of the pattern.
		 */
		@Singular Map<Locale, String> longDescriptions;
	}

	@Value
	@Builder
	public static class Links
	{
		/**
		 * A link to the Causal Diagram SVG for this pattern.
		 */
		String causalDiagramSvg;
	}
}
