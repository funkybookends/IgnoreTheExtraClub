package com.ignoretheextraclub.model.data;

import com.ignoretheextraclub.model.view.PageViewable;
import org.eclipse.mylyn.wikitext.core.parser.MarkupParser;
import org.eclipse.mylyn.wikitext.core.parser.builder.HtmlDocumentBuilder;
import org.eclipse.mylyn.wikitext.mediawiki.core.MediaWikiLanguage;
import org.springframework.data.annotation.CreatedDate;

import java.io.StringWriter;
import java.time.Instant;
import java.util.List;

/**
 * Created by caspar on 23/03/17.
 */
public class Post implements PageViewable
{
    private String id;
    private User author;
    private @CreatedDate Instant createdDate;
    private String title;
    private String subtitle;
    private String mediaWikiBody;

    public Post(final User author,
        final Instant createdDate,
        final String title,
        final String subtitle,
        final String mediaWikiBody)
    {
        this(null,
                author,
                createdDate,
                title,
                subtitle,
                mediaWikiBody);
    }

    public Post(final String id,
            final User author,
            final Instant createdDate,
            final String title,
            final String subtitle,
            final String mediaWikiBody)
    {
        this.id = id;
        this.author = author;
        this.createdDate = createdDate;
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

    @Override
    public String getPageTitle()
    {
        return getTitle();
    }

    public Instant getCreatedDate()
    {
        return createdDate;
    }

    @Override
    public boolean hasAlternativeNames()
    {
        return false;
    }

    @Override
    public List<String> getAlternativeNames()
    {
        return null;
    }

    @Override
    public boolean hasPageSubtitle()
    {
        return true;
    }

    @Override
    public String getPageSubtitle()
    {
        return getSubtitle();
    }

    @Override
    public boolean hasDetails()
    {
        return false;
    }

    @Override
    public List<Characteristic> getDetails()
    {
        return null;
    }

    @Override
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
}
