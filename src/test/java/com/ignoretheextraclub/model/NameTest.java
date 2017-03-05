package com.ignoretheextraclub.model;

import com.ignoretheextraclub.persistence.Patterns;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.FourHandedSiteswap;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by caspar on 08/02/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class NameTest
{
    private static final String NAME   = "Holy Grail";
    private static final String OPUS_1 = "Opus1";

    @Autowired
    private Patterns patterns;

    @Before
    public void setUp()
            throws Exception
    {
        patterns.deleteAll();
    }

    @Test(expected = DuplicateKeyException.class)
    public void GIVEN_aDuplicateName_EXPECT_DuplicateKeyException()
            throws Exception
    {
        final Pattern pattern = new Pattern(FourHandedSiteswap.create("975"));
        pattern.setName(new PatternName(NAME, 1, 1));
        pattern.setName(new PatternName(OPUS_1, 0, 0));

        patterns.save(pattern);

        Pattern pByName = patterns.findByName(NAME);
        Pattern pByOpus = patterns.findByName(OPUS_1);

        Assert.assertNotNull("pByOpus was null", pByOpus);
        Assert.assertNotNull("pByName was null", pByName);

        final Pattern sameName = new Pattern(FourHandedSiteswap.create("89A"));

        sameName.setName(new PatternName(NAME, 0, 4));

        Pattern throwError = patterns.save(sameName);
    }
}