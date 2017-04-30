package com.ignoretheextraclub.model.request;

import java.time.Instant;

/**
 * Created by caspar on 15/04/17.
 */
public class CommentRequest
{
    public final Instant createdDate = Instant.now();
    public String mediaWikiBody;
    private String patternId;

    public CommentRequest(String mediaWikiBody)
    {
        this.mediaWikiBody = mediaWikiBody;
    }

    public String getMediaWikiBody()
    {
        return mediaWikiBody;
    }

    public void setMediaWikiBody(String mediaWikiBody)
    {
        this.mediaWikiBody = mediaWikiBody;
    }

    public Instant getCreatedDate()
    {
        return createdDate;
    }

    public String getPatternId()
    {
        return patternId;
    }
}
