package com.ignoretheextraclub.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Comparator;

/**
 * Created by caspar on 06/02/17.
 */

public class PatternName
        implements Comparable
{
    private @Indexed(unique = true)     String  name;
    private              int     usages;
    private              int     weight; // Used to override the usages, if admin prefers
    private @CreatedDate Instant createdDate;

    public PatternName(final String name,
                       final int usages,
                       final int weight)
    {
        this.name = name;
        this.usages = usages;
        this.weight = weight;
        this.createdDate = Instant.now();
    }

    public String getName()
    {
        return name;
    }

    public int getUsages()
    {
        return usages;
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
            if (first.usages > second.usages)
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

    public void setUsages(int usages)
    {
        this.usages = usages;
    }

    @Override
    public int compareTo(Object o)
    {
        return sorter().compare(this, (PatternName) o);
    }
}
