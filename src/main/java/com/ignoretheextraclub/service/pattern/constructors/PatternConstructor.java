package com.ignoretheextraclub.service.pattern.constructors;

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
     * Constructs a new pattern and gives it names. If a PatternConstructor returns
     * a non empty optional for {@link #getNaturalName(String)} then it must return a Pattern.
     * If used incorrectly it may return null.
     * @param name
     * @return
     */
    Pattern createPattern(String name);

    /**
     * Returns the priority with which this pattern constructor should be used.
     *
     * Lower means it should be used first.
     * @return
     */
    int getPriority();
}
