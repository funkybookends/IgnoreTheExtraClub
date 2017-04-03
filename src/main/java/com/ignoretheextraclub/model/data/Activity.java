package com.ignoretheextraclub.model.data;

import org.eclipse.mylyn.wikitext.core.parser.MarkupParser;
import org.eclipse.mylyn.wikitext.core.parser.builder.HtmlDocumentBuilder;
import org.eclipse.mylyn.wikitext.mediawiki.core.MediaWikiLanguage;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.StringWriter;
import java.time.Instant;
import java.util.List;

/**
 * Created by caspar on 23/03/17.
 */
@Document(collection = "activity")
public class Activity
{
    private String id;
    private User author;
    private @CreatedDate Instant createdDate;
    private List<String> tags;
    private String title;
    private String subtitle;
    private String mediaWikiBody;

    public Activity(final User author,
            final Instant createdDate,
            final String title,
            final String subtitle,
            final String mediaWikiBody,
            final List<String> tags)
    {
        this(null,
                author,
                createdDate,
                tags,
                title,
                subtitle,
                mediaWikiBody);
    }

    public Activity(final String id,
            final User author,
            final Instant createdDate,
            final List<String> tags,
            final String title,
            final String subtitle,
            final String mediaWikiBody)
    {
        this.id = id;
        this.author = author;
        this.createdDate = createdDate;
        this.tags = tags;
        this.title = title;
        this.subtitle = subtitle;
        this.mediaWikiBody = mediaWikiBody;
    }

    public String getId()
    {
        return id;
    }

    public void setId(final String id)
    {
        this.id = id;
    }

    public User getAuthor()
    {
        return author;
    }

    public void setAuthor(final User author)
    {
        this.author = author;
    }

    public String getPageTitle()
    {
        return getTitle();
    }

    public Instant getCreatedDate()
    {
        return createdDate;
    }

    public boolean hasAlternativeNames()
    {
        return false;
    }

    public List<String> getAlternativeNames()
    {
        return null;
    }

    public boolean hasPageSubtitle()
    {
        return true;
    }

    public String getPageSubtitle()
    {
        return getSubtitle();
    }

    public boolean hasDetails()
    {
        return false;
    }

    public List<Characteristic> getDetails()
    {
        return null;
    }

    public String getBody()
    {
        return getBodyHtml();
    }

    public void setCreatedDate(final Instant createdDate)
    {
        this.createdDate = createdDate;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(final String title)
    {
        this.title = title;
    }

    public String getSubtitle()
    {
        return subtitle;
    }

    public void setSubtitle(final String subtitle)
    {
        this.subtitle = subtitle;
    }

    public String getMediaWikiBody()
    {
        return mediaWikiBody;
    }

    public void setMediaWikiBody(final String mediaWikiBody)
    {
        this.mediaWikiBody = mediaWikiBody;
    }

    // http://help.eclipse.org/luna/index.jsp?topic=%2Forg.eclipse.mylyn.wikitext.help.ui%2Fhelp%2Fdevguide%2FWikiText+Developer+Guide.html
    public String getBodyHtml()
    {
        StringWriter writer = new StringWriter();

        HtmlDocumentBuilder builder = new HtmlDocumentBuilder(writer);
        builder.setEmitAsDocument(false);

        MarkupParser parser = new MarkupParser(new MediaWikiLanguage());
        parser.setBuilder(builder);
        parser.parse(mediaWikiBody);

        return writer.toString();
    }

    private String getPageLink()
    {
        if (tags.contains("new-pattern"))
        {
            return "/p/" + getTitle();
        }
        else
        {
            return "/" + getTitle();
        }
    }
}
