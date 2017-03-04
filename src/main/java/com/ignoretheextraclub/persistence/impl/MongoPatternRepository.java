package com.ignoretheextraclub.persistence.impl;

import com.ignoretheextraclub.exception.UnknownSiteswapTypeException;
import com.ignoretheextraclub.model.Pattern;
import com.ignoretheextraclub.model.PatternName;
import com.ignoretheextraclub.persistence.Patterns;
import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.siteswap.AbstractSiteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.FourHandedSiteswap;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Indexes;
import org.bson.Document;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

/**
 * Created by caspar on 02/03/17.
 */
public class MongoPatternRepository implements Patterns
{
    private static final String SITESWAP = "siteswap";
    private static final String SITESWAP_TYPE = "siteswap_type";
    private static final String CREATED_DATE = "created_date";
    private static final String NAMES = "names";
    private static final String NAME = "name";
    private static final String USAGES = "usages";
    private static final String WEIGHT = "weight";
    private static final String INDEX_SEPARATOR = ".";
    private static final String NAMES_NAME = NAMES + INDEX_SEPARATOR + NAME;
    private static final String SECONDS = "seconds";
    private static final String NANO = "nano";

    private final MongoCollection<Document> collection;

    public MongoPatternRepository(MongoCollection<Document> collection)
    {
        this.collection = collection;
        this.collection.createIndex(Indexes.ascending(NAMES_NAME));
    }

    @Override
    public void save(Pattern pattern)
    {
        collection.insertOne(convertToDocument(pattern));
    }

    @Override
    public Pattern get(String name, Class<? extends AbstractSiteswap> siteswapType)
    {
        return convertToPattern(collection.find().first(), siteswapType);
//        return convertToPattern(collection.find(eq(NAMES_NAME, name)).first(),siteswapType);
    }

    @SuppressWarnings("unchecked")
    private Pattern convertToPattern(final Document patternDoc, Class<? extends AbstractSiteswap> siteswapType)
    {
        AbstractSiteswap siteswap;
        try
        {
            if (siteswapType == FourHandedSiteswap.class)
            {
                String string = patternDoc.getString(SITESWAP);
                siteswap = FourHandedSiteswap.create(string);
            }
            else
            {
                throw new UnknownSiteswapTypeException();
            }
        }
        catch (InvalidSiteswapException e)
        {
            throw new UnknownSiteswapTypeException(e);
        }

        List<PatternName> names = ((List<Document>) patternDoc.get(NAMES)).stream()
                .map(this::convertToPatternName)
                .sorted(PatternName.sorter())
                .collect(Collectors.toList());

        return new Pattern(siteswap, names, convertToInstant((Document) patternDoc.get(CREATED_DATE)));
    }

    private PatternName convertToPatternName(Document document)
    {
        return new PatternName(document.getString(NAME),
                               document.getInteger(USAGES),
                               document.getInteger(WEIGHT));
    }

    @Override
    public List<Pattern> get(String name)
    {
        return null;
    }

    private Document convertToDocument(final Pattern pattern)
    {
        return new Document(SITESWAP, pattern.getSiteswap().toString())
                .append(SITESWAP_TYPE, pattern.getSiteswap().getClass().getSimpleName())
                .append(CREATED_DATE, pattern.getCreatedDate() == null ? convertToDocument(Instant.now()) : convertToDocument(pattern.getCreatedDate()))
                .append(NAMES, pattern.getNames().stream().map(this::convertToDocument).collect(Collectors.toList()));
    }

    private Document convertToDocument(final PatternName patternName)
    {
        return new Document(NAME, patternName.getName())
                .append(USAGES, patternName.getUsages())
                .append(WEIGHT, patternName.getWeight())
                .append(CREATED_DATE,
                        patternName.getCreatedDate() == null ? convertToDocument(Instant.now()) : convertToDocument(patternName.getCreatedDate()));
    }

    private Document convertToDocument(final Instant instant)
    {
        return new Document(SECONDS, instant.getEpochSecond())
                .append(NANO, instant.getNano());
    }

    private Instant convertToInstant(final Document instantDoc)
    {
        return Instant.ofEpochSecond(instantDoc.getLong(SECONDS),
                                     instantDoc.getInteger(NANO));
    }
}
