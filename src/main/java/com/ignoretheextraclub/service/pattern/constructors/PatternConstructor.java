package com.ignoretheextraclub.service.pattern.constructors;

import com.ignoretheextraclub.model.juggling.Pattern;
import org.springframework.core.PriorityOrdered;

import java.util.Optional;

/**
 * Created by caspar on 05/03/17.
 */
public interface PatternConstructor extends PriorityOrdered
{
    /**
     * Returns the name that it would always give the pattern if it were to construct it.
     *
     * @param name
     *
     * @return A string name
     */
    Optional<String> getNaturalName(String name);

    /**
     * Constructs a new pattern and gives it names. If a PatternConstructor returns
     * a non empty optional for {@link #getNaturalName(String)} then it must return a Pattern.
     * If used incorrectly it may return null.
     *
     * @param name
     *
     * @return The pattern for the name or null
     */
    Pattern createPattern(String name);
}
