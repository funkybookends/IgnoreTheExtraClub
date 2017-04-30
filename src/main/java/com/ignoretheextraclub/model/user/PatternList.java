package com.ignoretheextraclub.model.user;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Created by caspar on 14/04/17.
 */
public class PatternList
{
    private final String name;
    private final boolean publiclyVisible;
    private final List<PatternListItem> items;
    private final Instant createdDate;
    private final Instant lastEditedDate;

    public PatternList(String name,
                       boolean publiclyVisible,
                       List<PatternListItem> items,
                       Instant createdDate,
                       Instant lastEditedDate)
    {
        this.name = name;
        this.publiclyVisible = publiclyVisible;
        this.items = items;
        this.createdDate = createdDate;
        this.lastEditedDate = lastEditedDate;
    }

    public String getName()
    {
        return name;
    }

    public boolean isPubliclyVisible()
    {
        return publiclyVisible;
    }

    public Instant getCreatedDate()
    {
        return createdDate;
    }

    public Instant getLastEditedDate()
    {
        return lastEditedDate;
    }

    public boolean addPatternListItem(final PatternListItem patternListItem)
    {
        return !containsPatternId(patternListItem.getPatternId()) &&
                this.items.add(patternListItem);
    }

    public boolean containsPatternId(final String patternId)
    {
        return getItemByPatternId(patternId).isPresent();
    }

    public Optional<PatternListItem> getItemByPatternId(final String patternId)
    {
        return items.stream()
                    .filter(pli -> pli.getPatternId().equals(patternId))
                    .findFirst();
    }

    public List<PatternListItem> getItems()
    {
        return items;
    }

    /**
     * Removes the pattern from the items
     *
     * @param patternId
     *
     * @return <tt>true</tt> if this list contained the specified pattern
     */
    public boolean removePatternById(String patternId)
    {
        return getItemByPatternId(patternId)
                .map(items::remove)
                .orElse(false);
    }
}
