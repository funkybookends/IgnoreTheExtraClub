package com.ignoretheextraclub.model;

import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.siteswap.AbstractSiteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.FourHandedSiteswap;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by caspar on 06/02/17.
 */
@Document(collection = "patterns")
public class Pattern
{
    public static final String NAMES_FIELD = "pattern_names";

    private final                     String            id;
    private final @Transient          AbstractSiteswap  siteswap;
    private final                     String            siteswapConstructor;
    private final                     String            siteswapTypeSimpleName;
    private final @Field(NAMES_FIELD) List<PatternName> names;
    private final @CreatedDate        Instant           createdDate;

    public Pattern(AbstractSiteswap siteswap)
    {
        this.siteswap = siteswap;
        this.names = new ArrayList<>();
        this.id = null;
        this.createdDate = Instant.now();

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
            List<PatternName> names,
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

    public void setName(final PatternName patternName)
    {
        this.names.add(patternName);
    }
}
