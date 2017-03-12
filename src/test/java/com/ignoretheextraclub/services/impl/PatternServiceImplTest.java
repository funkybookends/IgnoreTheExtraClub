package com.ignoretheextraclub.services.impl;

import com.ignoretheextraclub.model.Pattern;
import com.ignoretheextraclub.model.PatternName;
import com.ignoretheextraclub.persistence.repository.PatternRepository;
import com.ignoretheextraclub.services.PatternService;
import com.ignoretheextraclub.services.patternconstructors.PatternConstructor;
import com.ignoretheextraclub.services.patternconstructors.impl.FourHandedSiteswapPatternConstructor;
import com.ignoretheextraclub.services.patternconstructors.impl.TwoHandedSiteswapPatternConstructor;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.FourHandedSiteswap;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by caspar on 08/03/17.
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class PatternServiceImplTest
{
    // Service under test
    private PatternService patternService;

    private @Autowired PatternRepository patternRepository;

    // Mocks
    private PatternConstructor pc1;
    private PatternConstructor pc2;

    @Before
    public void setUp() throws Exception
    {
        pc1 = spy(new FourHandedSiteswapPatternConstructor());
        pc2 = spy(new TwoHandedSiteswapPatternConstructor());

        patternService = new PatternServiceImpl(patternRepository, Arrays.asList(pc1, pc2));
        patternRepository.deleteAll();
    }

    @Test
    public void GIVEN_noPattern_EXPECT_empty() throws Exception
    {
        Assert.assertFalse(patternService.get("any name").isPresent());
    }

    @Test
    public void GIVEN_pattern_EXPECT_pattern() throws Exception
    {
        final String name = "name";
        final Pattern pattern = new Pattern(FourHandedSiteswap.create("975"),
                                            new PatternName(name, 0));
        patternRepository.save(pattern);
        Assert.assertTrue(patternService.get(name).isPresent());
    }

    @Test
    public void GIVEN_pc1CantConstruct_EXPECT_pc2IsCalled_AND_patternIsSaved() throws Exception
    {
        String name = "345";
        final Pattern actual = patternService.getOrCreate(name);

        verify(pc1, times(1)).getNaturalName(name);
        verify(pc1, times(0)).createPattern(name);
        verify(pc2, times(1)).getNaturalName(name);
        verify(pc2, times(1)).createPattern(name);
    }

    @Test
    public void GIVEN_save_EXPECT_saved() throws Exception
    {
        Assert.assertFalse(patternRepository.findByName("name").isPresent());

        final Pattern pattern = new Pattern(FourHandedSiteswap.create("975"),
                                      new PatternName("name", 0));

        patternService.save(pattern);

        Assert.assertTrue(patternRepository.findByName("name").isPresent());
        Assert.assertTrue(patternService.get("name").isPresent());
    }

    @Test
    public void GIVEN_newerPatterns_EXPECT_pagesCorrectly() throws Exception
    {
        final String[] siteswaps = {"975", "678", "9A8", "756", "78686", "7868686"};

        for (String siteswap : siteswaps)
        {
            patternService.getOrCreate(siteswap);
        }

        Page<Pattern> newest = patternService.newest(1, 2);

        long totalElements = newest.getTotalElements();
        Assert.assertEquals(siteswaps.length, totalElements);

        Assert.assertEquals(3,newest.getTotalPages());

        ArrayList<Pattern> patterns = new ArrayList<>();
        newest.forEach(patterns::add);

        Assert.assertEquals(2, patterns.size());
        Assert.assertEquals("756", patterns.get(0).getSiteswap().toString());
        Assert.assertEquals("9A8", patterns.get(1).getSiteswap().toString());
    }
}