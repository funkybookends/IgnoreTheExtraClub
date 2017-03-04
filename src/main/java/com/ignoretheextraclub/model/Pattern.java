package com.ignoretheextraclub.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ignoretheextraclub.siteswapfactory.siteswap.AbstractSiteswap;
import org.joda.time.DateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * Created by caspar on 06/02/17.
 */
@Document(collection = "patterns")
public class Pattern
{
    public static final String COLLECTION_NAME = "patterns";

    private String id;
    private AbstractSiteswap siteswap;

    private List<PatternName> names;
    private Instant createdDate;

    public Pattern(AbstractSiteswap siteswap)
    {
        this.siteswap = siteswap;
        this.names = new ArrayList<>();
    }

    @PersistenceConstructor
    public Pattern(AbstractSiteswap siteswap,
                   List<PatternName> names,
                   Instant createdDate)
    {
        this.siteswap = siteswap;
        this.names = names == null ? new ArrayList<>() : names;
        this.createdDate = createdDate;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
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
        return names.stream().collect(Collectors.toList());
    }

    public void setName(final PatternName patternName)
    {
        this.names.add(patternName);
    }
}
