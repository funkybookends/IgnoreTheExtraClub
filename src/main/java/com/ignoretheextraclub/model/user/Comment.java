package com.ignoretheextraclub.model.user;

import com.ignoretheextraclub.model.juggling.Pattern;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import java.time.Instant;

/**
 * Created by caspar on 14/04/17.
 */
public class Comment
{
    private @Id String id;
    private @Transient Pattern pattern;
    private String mediaWikiBody;
    private String username;
    private Instant createdDate;
    private boolean visible;

    public Comment(final String mediaWikiBody,
               final String username,
               final Instant createdDate,
               final boolean visible)
    {
        this(null, mediaWikiBody, username, createdDate, visible);
    }

    public Comment(final String id,
                   final String mediaWikiBody,
                   final String username,
                   final Instant createdDate,
                   final boolean visible)
    {
        this.id = id;
        this.mediaWikiBody = mediaWikiBody;
        this.username = username;
        this.createdDate = createdDate;
        this.visible = visible;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getMediaWikiBody()
    {
        return mediaWikiBody;
    }

    public void setMediaWikiBody(String mediaWikiBody)
    {
        this.mediaWikiBody = mediaWikiBody;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public Instant getCreatedDate()
    {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate)
    {
        this.createdDate = createdDate;
    }

    public boolean isVisible()
    {
        return visible;
    }

    public void setVisible(boolean visible)
    {
        this.visible = visible;
    }

    public Pattern getPattern()
    {
        return pattern;
    }

    public void setPattern(final Pattern pattern)
    {
        this.pattern = pattern;
    }
}
