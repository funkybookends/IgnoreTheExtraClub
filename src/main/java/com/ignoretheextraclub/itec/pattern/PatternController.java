package com.ignoretheextraclub.itec.pattern;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ignoretheextraclub.itec.exception.UnknownPatternTypeException;
import com.ignoretheextraclub.itec.siteswap.SiteswapType;

@RestController
public class PatternController
{
	private static final String TYPE = "type";
	private static final String NAME = "name";

	private final PatternService patternService;

	public PatternController(final PatternService patternService)
	{
		this.patternService = patternService;
	}

	/**
	 * Gets a pattern of a specific type.
	 * <p>
	 * Given the specified type, and name or siteswap, finds the pattern
	 * that matches this request, populates its information and returns it.
	 * </p>
	 * <p>
	 * Currently configured types:
	 * <dl>
	 * <dt>fhs</dt>
	 * <dd>A Four Handed Siteswap, e.g. 975</dd>
	 * <dt>ths</dt>
	 * <dd>A Two Handed Vanilla Siteswap, e.g. 534</dd>
	 * <dt>passing</dt>
	 * <dd>A passing siteswap, e.g. &lt;3p|3p&gt;&lt;3|3&gt;</dd>
	 * </dl>
	 * </p>
	 *
	 * @param type The type of pattern
	 * @param name The name or siteswap of the pattern
	 *
	 * @return A pattern
	 *
	 * @throws UnknownPatternTypeException If the type is not mapped to a type.
	 */
	@GetMapping(path = "/p/{" + TYPE + "}/{" + NAME + "}")
	public Pattern getPatternByType(@PathVariable(TYPE) final String type,
	                                @PathVariable(NAME) final String name)
		throws UnknownPatternTypeException
	{
		return patternService.getPattern(SiteswapType.resolveType(type), name);
	}

	/**
	 * Gets a pattern of unknown type.
	 * <p>
	 * Finds the pattern
	 * that matches this request, populates its information and returns it.
	 * </p>
	 * <p>
	 * Currently Types are examined in this order:
	 * <ol>
	 * <li>A Two Handed Vanilla Siteswap, e.g. 534</li>
	 * <li>A Sync Two Handed Siteswap, e.g. (6x,4)*</li>
	 * <li>A Four Handed Siteswap, e.g. 975</li>
	 * <li>A passing siteswap, e.g. &lt;3p|3p&gt;&lt;3|3&gt;</li>
	 * </ol>
	 * </p>
	 *
	 * @param name The name or siteswap of the pattern
	 *
	 * @return A pattern
	 */
	@GetMapping(path = "/p/{" + NAME + "}")
	public Pattern getPattern(@PathVariable(NAME) final String name)
	{
		return patternService.getPattern(name);
	}
}
