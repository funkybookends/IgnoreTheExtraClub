package com.ignoretheextraclub.model;

import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.siteswap.AbstractSiteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.FourHandedSiteswap;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.util.Assert;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * Created by caspar on 06/02/17.
 */
@Document(collection = "patterns")
public class Pattern
{
    public static final String NAMES_FIELD = "pattern_names";

    private final                     String           id;
    private final @Transient          AbstractSiteswap siteswap;
    private final                     String           siteswapConstructor;
    private final                     String           siteswapTypeSimpleName;
    private final @Field(NAMES_FIELD) TreeSet<PatternName> names;
    private final @CreatedDate        Instant          createdDate;

    public Pattern(final AbstractSiteswap siteswap, final PatternName... names)
    {
       this(siteswap, asSet(names));
    }

    public Pattern(final AbstractSiteswap siteswap, final TreeSet<PatternName> names)
    {
        Assert.notEmpty(names, "Must have at least one name.");
        this.siteswap = siteswap;
        this.id = null;
        this.createdDate = Instant.now();
        this.names = names;

        if (siteswap.getClass() == FourHandedSiteswap.class)
        {
            this.siteswapTypeSimpleName = FourHandedSiteswap.class.getSimpleName();
            this.siteswapConstructor = siteswap.toString();
        }
        else
        {
            throw new UnsupportedOperationException(
                    "This siteswap type is not supported.");
        }
    }

    @PersistenceConstructor
    Pattern(String id,
            String siteswapConstructor,
            String siteswapTypeSimpleName,
            TreeSet<PatternName> names,
            Instant createdDate)
            throws
            InvalidSiteswapException
    {
        this.id = id;
        this.siteswapConstructor = siteswapConstructor;
        this.siteswapTypeSimpleName = siteswapTypeSimpleName;
        this.names = names;
        this.createdDate = createdDate;

        if (siteswapTypeSimpleName.equals(
                FourHandedSiteswap.class.getSimpleName()))
        {
            this.siteswap = FourHandedSiteswap.create(this.siteswapConstructor);
        }
        else
        {
            throw new UnsupportedOperationException(
                    "Could not reconstruct siteswap.");
        }
    }

    public String getId()
    {
        return id;
    }

    public AbstractSiteswap getSiteswap()
    {
        return siteswap;
    }

    public Instant getCreatedDate()
    {
        return createdDate;
    }

    public List<PatternName> getNames()
    {
        return names.stream()
                    .collect(Collectors.toList());
    }

    public Optional<PatternName> getName(final String name)
    {
        return names.stream().filter(patternName -> patternName.getName().equals(name)).findFirst();
    }

    public void setName(final PatternName patternName)
    {
        this.names.add(patternName);
    }

    private static TreeSet<PatternName> asSet(final PatternName... names)
    {
        return Arrays.stream(names).collect(Collectors.toCollection(() -> new TreeSet<PatternName>(PatternName.sorter())));
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }

        Pattern pattern = (Pattern) o;

        return siteswap.equals(pattern.siteswap);
    }

    @Override
    public int hashCode()
    {
        return siteswap.hashCode();
    }
}
