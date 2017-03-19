package com.ignoretheextraclub.services.patternconstructors;

import com.ignoretheextraclub.model.data.Pattern;

import java.util.Optional;

/**
 * Created by caspar on 05/03/17.
 */
public interface PatternConstructor
{
    /**
     * Returns the name that it would always give the pattern if it were to construct it.
     * @param name
     * @return
     */
    Optional<String> getNaturalName(String name);

    /**
     * Constructs a new pattern and gives it names. If a PatternConstructor reuturns
     * a non empty optional for {@link #getNaturalName(String)} then it must return a Pattern.
     * If used incorrectly it may return null.
     * @param name
     * @return
     */
    Pattern createPattern(String name);
}
