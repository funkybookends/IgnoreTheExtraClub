package com.ignoretheextraclub.model.user;

import java.time.Instant;

/**
 * Created by caspar on 14/04/17.
 */
public class PatternListItem
{
    private final String patternId;
    private final String patternTitle;
    private final Instant createdDate;

    public PatternListItem(String patternId,
                           String patternTitle,
                           Instant createdDate)
    {
        this.patternId = patternId;
        this.patternTitle = patternTitle;
        this.createdDate = createdDate;
    }

    public String getPatternId()
    {
        return patternId;
    }

    public String getPatternTitle()
    {
        return patternTitle;
    }

    public Instant getCreatedDate()
    {
        return createdDate;
    }
}
