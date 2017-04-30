package com.ignoretheextraclub.model.user;

import org.springframework.data.annotation.Id;

import java.time.Instant;

/**
 * Created by caspar on 14/04/17.
 */
public class Juggled
{
    private @Id final String id;
    private final Instant createdDate;
    private final String withUserName;
    private final String patternId;
    private final String where;

    public Juggled(final Instant createdDate,
               final String withUserName,
               final String patternId,
               final String where)
    {
        this(null, createdDate, withUserName, patternId, where);
    }

    public Juggled(final String id,
                   final Instant createdDate,
                   final String withUserName,
                   final String patternId,
                   final String where)
    {
        this.id = id;
        this.createdDate = createdDate;
        this.withUserName = withUserName;
        this.patternId = patternId;
        this.where = where;
    }

    public String getId()
    {
        return id;
    }

    public Instant getCreatedDate()
    {
        return createdDate;
    }

    public String getWithUserName()
    {
        return withUserName;
    }

    public String getPatternId()
    {
        return patternId;
    }

    public String getWhere()
    {
        return where;
    }
}
