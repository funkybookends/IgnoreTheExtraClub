package com.ignoretheextraclub.model.juggling;

import com.ignoretheextraclub.model.user.Comment;
import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.siteswap.AbstractSiteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.FourHandedSiteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.TwoHandedSiteswap;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.util.Assert;

import java.time.Instant;
import java.util.ArrayList;
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
    private static final Class<?> FOUR_HANDED_SITESWAP = FourHandedSiteswap.class;
    private static final Class<?> TWO_HANDED_SITESWAP = TwoHandedSiteswap.class;

    private final String id;
    private @Transient final AbstractSiteswap siteswap;
    private final String siteswapConstructor;
    private final String siteswapTypeSimpleName;
    private @Field(NAMES_FIELD) final TreeSet<PatternName> names;
    private @CreatedDate final Instant createdDate;
    private final List<Comment> comments;

    public Pattern(final AbstractSiteswap siteswap,
                   final PatternName... names)
    {
        this(siteswap, asSet(names));
    }

    public Pattern(final AbstractSiteswap siteswap,
                   final TreeSet<PatternName> names)
    {
        Assert.notEmpty(names, "Must have at least one name.");
        this.siteswap = siteswap;
        this.id = null;
        this.createdDate = Instant.now();
        this.names = names;
        this.comments = new ArrayList<>();

        if (siteswap.getClass() == FOUR_HANDED_SITESWAP || siteswap.getClass() == TWO_HANDED_SITESWAP)
        {
            this.siteswapTypeSimpleName = siteswap.getClass()
                                                  .getSimpleName();
            this.siteswapConstructor = siteswap.toString();
        }
        else
        {
            throw new UnsupportedOperationException(
                    "This siteswap type is not supported.");
        }
    }

    private static TreeSet<PatternName> asSet(final PatternName... names)
    {
        return Arrays.stream(names)
                     .collect(Collectors.toCollection(() -> new TreeSet<>(PatternName.sorter())));
    }

    @PersistenceConstructor
    public Pattern(final String id,
                   final String siteswapConstructor,
                   final String siteswapTypeSimpleName,
                   final TreeSet<PatternName> names,
                   final Instant createdDate,
                   final List<Comment> comments) throws InvalidSiteswapException
    {
        this.id = id;
        this.siteswapConstructor = siteswapConstructor;
        this.siteswapTypeSimpleName = siteswapTypeSimpleName;
        this.names = names;
        this.createdDate = createdDate;
        this.comments = comments;

        if (siteswapTypeSimpleName.equals(FourHandedSiteswap.class.getSimpleName()))
        {
            this.siteswap = FourHandedSiteswap.create(this.siteswapConstructor);
        }
        else if (siteswapTypeSimpleName.equals(TwoHandedSiteswap.class.getSimpleName()))
        {
            this.siteswap = TwoHandedSiteswap.create(this.siteswapConstructor);
        }
        else
        {
            throw new UnsupportedOperationException("Could not reconstruct siteswap.");
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

    public List<PatternName> getNames()
    {
        return names.stream()
                    .collect(Collectors.toList());
    }

    public Optional<PatternName> getName(final String name)
    {
        return names.stream()
                    .filter(patternName -> patternName.getName().equals(name))
                    .findFirst();
    }

    public void setName(final PatternName patternName)
    {
        this.names.add(patternName);
    }

    public Instant getCreatedDate()
    {
        return createdDate;
    }

    public boolean hasAlternativeNames()
    {
        return names.size() > 1;
    }

    public List<String> getAlternativeNames()
    {
        return names.stream()
                    .map(PatternName::getName)
                    .collect(Collectors.toList());
    }

    public boolean hasPageSubtitle()
    {
        return false;
    }

    public String getPageSubtitle()
    {
        return null;
    }

    public boolean hasDetails()
    {
        return true;
    }

    public List<Characteristic> getDetails()
    {
        // TODO refactor into something cleverer
        final List<Characteristic> characteristics = new ArrayList<>();

        characteristics.add(new Characteristic("Period", siteswap.getPeriod()));
        characteristics.add(new Characteristic("Objects", siteswap.getNumObjects()));
        characteristics.add(new Characteristic("Grounded", siteswap.isGrounded()));

        if (siteswap.getClass() == FOUR_HANDED_SITESWAP)
        {
            FourHandedSiteswap fhs = (FourHandedSiteswap) this.siteswap;
            characteristics.add(new Characteristic("Siteswap", fhs.getStringSiteswap()));
            characteristics.add(new Characteristic("Leader Clubs",
                    fhs.getLeaderStartingFirstHandObjects() + " + " + fhs.getLeaderStartingSecondHandObjects()));
            characteristics.add(new Characteristic("Follower Clubs",
                    fhs.getFollowerStartingFirstHandObjects() + " + " + fhs.getFollowerStartingSecondHandObjects()));
        }
        else if (siteswap.getClass() == TWO_HANDED_SITESWAP)
        {
            TwoHandedSiteswap ths = (TwoHandedSiteswap) this.siteswap;
            characteristics.add(new Characteristic("Siteswap", ths.getStringSiteswap()));
            characteristics.add(new Characteristic("Hands",
                    ths.getFirstStartingHandObjects() + " + " + ths.getSecondStartingHandObjects()));
        }

        return characteristics;
    }

    public String getBody()
    {
        return "I'm a description";
    }

    public String getMiniTitle()
    {
        return getPageTitle();
    }

    public String getPageTitle()
    {
        return names.iterator()
                    .next()
                    .getName(); // Returns the first name
    }

    public String getMiniSubtitle()
    {
        if (siteswap.getClass() == FourHandedSiteswap.class)
        {
            return ((FourHandedSiteswap) siteswap).getLeaderPrechac();
        }
        else
        {
            return siteswap.toString(); // TODO improve
        }
    }

    public String getMicroTitle()
    {
        return getPageTitle();
    }

    public String getMicroDescription()
    {
        return getMiniDescription();
    }

    public String getMiniDescription()
    {
        if (siteswap.getClass() == FOUR_HANDED_SITESWAP)
        {
            FourHandedSiteswap fhs = (FourHandedSiteswap) siteswap;
            return fhs.getLeaderPrechac(); // TODO replace with hefflish
        }
        else if (siteswap.getClass() == TWO_HANDED_SITESWAP)
        {
            TwoHandedSiteswap ths = (TwoHandedSiteswap) siteswap;
            return ths.getStringSiteswap(); // TODO make better
        }

        return "";
    }

    public List<Comment> getComments()
    {
        return comments;
    }

    public boolean addComment(final Comment comment)
    {
        return this.comments.add(comment);
    }

    public int hashCode()
    {
        return siteswap.hashCode();
    }

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
}
