package com.ignoretheextraclub.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.Instant;
import java.util.Comparator;

/**
 * Created by caspar on 06/02/17.
 */

public class PatternName
        implements Comparable
{
    private @Indexed(unique = true)     String  name;
    private              int     weight; // Used to override the usages, if admin prefers
    private @CreatedDate Instant createdDate;

    public PatternName(final String name,
                       final int weight)
    {
        this.name = name;
        this.weight = weight;
        this.createdDate = Instant.now();
    }

    public String getName()
    {
        return name;
    }

    public int getWeight()
    {
        return weight;
    }

    public Instant getCreatedDate()
    {
        return createdDate;
    }

    public static Comparator<PatternName> sorter()
    {
        return (first, second) ->
        {
            if (first.weight > second.weight)
            {
                return -1;
            }
            return first.name.compareTo(second.name);
        };
    }

    public static String getId(final String siteswapType,
                               final String name)
    {
        return siteswapType + ":" + name;
    }

    @Override
    public int compareTo(Object o)
    {
        return sorter().compare(this, (PatternName) o);
    }
}
