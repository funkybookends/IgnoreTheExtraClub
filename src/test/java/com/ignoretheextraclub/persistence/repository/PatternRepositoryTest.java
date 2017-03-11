package com.ignoretheextraclub.persistence.repository;

import com.ignoretheextraclub.model.Pattern;
import com.ignoretheextraclub.model.PatternName;
import com.ignoretheextraclub.siteswapfactory.siteswap.AbstractSiteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.FourHandedSiteswap;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

/**
 * Created by caspar on 06/03/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PatternRepositoryTest
{
    private static final String NAME1 = "name1";
    private static final String NAME2 = "name2";

    private AbstractSiteswap siteswap;

    @Autowired
    private PatternRepository patternRepository;

    @Before
    public void setUp() throws Exception
    {
        patternRepository.deleteAll();
        siteswap = FourHandedSiteswap.create("975");
    }

    @Test(expected = IllegalArgumentException.class)
    public void GIVEN_aPatternWithNoNames_EXPECT_cantConstruct() throws Exception
    {
        final Pattern constuctedPattern = new Pattern(siteswap);
    }

    @Test
    public void GIVEN_aPatternSave_EXPECT_readable() throws Exception
    {
        final Pattern expected = new Pattern(siteswap, new PatternName(NAME1,0));

        final Pattern save = patternRepository.save(expected);
        final String id = save.getId();

        final Pattern one = patternRepository.findOne(id);
        final Optional<Pattern> nameOptional = patternRepository.findByName(NAME1);

        Assert.assertTrue(nameOptional.isPresent());
        final Pattern name = nameOptional.get();
        Assert.assertEquals(expected.getSiteswap(), one.getSiteswap());
        Assert.assertEquals(expected.getSiteswap(), name.getSiteswap());

        Assert.assertEquals(id, one.getId());
        Assert.assertEquals(id, name.getId());
    }
}