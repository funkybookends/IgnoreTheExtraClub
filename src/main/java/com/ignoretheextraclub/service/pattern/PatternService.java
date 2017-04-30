package com.ignoretheextraclub.service.pattern;

import com.ignoretheextraclub.model.juggling.Pattern;
import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import org.springframework.data.domain.Page;

import java.util.Optional;

/**
 * Created by caspar on 05/03/17.
 */
public interface PatternService
{
    int DEFAULT_PAGE_SIZE = 10;

    /**
     * Attempts to retrieve the pattern by the name.
     *
     * @param name
     *
     * @return An optional of the pattern or empty if not found.
     */
    Optional<Pattern> get(String name);

    /**
     * Attempts to get the pattern by name, and if it is not available will try
     * and create it.
     * <p>
     * This method will save it before returning it.
     *
     * @param name
     *
     * @return The found or created pattern
     *
     * @throws InvalidSiteswapException if it cannot construct a pattern from
     *                                  the name
     */
    Pattern getOrCreate(String name) throws InvalidSiteswapException;

    /**
     * Saves a given pattern. You should use the new version as fields may have
     * changed.
     *
     * @param pattern
     *
     * @return the same pattern.
     */
    Pattern save(Pattern pattern);

    /**
     * Returns the newest patterns
     *
     * @param page the page number to return
     * @param size the number in the page
     *
     * @return The page of newest patterns
     */
    Page<Pattern> newest(int page,
                         int size);

    /**
     * Returns the newest patterns with the {@link #DEFAULT_PAGE_SIZE}.
     *
     * @param page
     *
     * @return
     */
    Page<Pattern> newest(int page);

    Optional<Pattern> getById(String patternId);
}
