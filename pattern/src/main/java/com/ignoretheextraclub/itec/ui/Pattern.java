package com.ignoretheextraclub.itec.ui;

import java.util.Locale;
import java.util.Map;
import java.util.Set;

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
	 * The title of the pattern. This represents ItEC's canonical name for the
	 * pattern.
	 */
	String title;

	/**
	 * The type of pattern. This value can be used in requests for the pattern
	 */
	String type;

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

	/**
	 * Alternative names for the pattern. These could be colloquial, or other
	 * representations of the pattern.
	 */
	@Singular
	Set<String> names;

	/**
	 * A map of Locale to a short description max 140 chars.
	 */
	@Singular
	Map<Locale, String> shortDescriptions;

	/**
	 * A map of Locale to a longer description of the pattern.
	 */
	@Singular
	Map<Locale, String> longDescriptions;
	/**
	 * A link to the Causal Diagram SVG for this pattern. Note that the
	 * header <b>"Accept: image/svg+xml"</b> must be sent.
	 */
	String causalDiagramSvg;

}
