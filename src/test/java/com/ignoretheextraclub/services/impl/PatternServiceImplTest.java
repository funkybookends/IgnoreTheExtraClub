package com.ignoretheextraclub.services.impl;

import com.ignoretheextraclub.model.Pattern;
import com.ignoretheextraclub.model.PatternName;
import com.ignoretheextraclub.persistence.repository.PatternRepository;
import com.ignoretheextraclub.services.PatternService;
import com.ignoretheextraclub.services.patternconstructors.PatternConstructor;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.FourHandedSiteswap;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.mock;
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
        pc1 = mock(PatternConstructor.class);
        pc2 = mock(PatternConstructor.class);

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
        final String name = "Holy Grail";
        final String naturalName = "975";
        final Pattern expectedPattern = new Pattern(FourHandedSiteswap.create("975"),
                                                  new PatternName(name, 0));

        when(pc1.getNaturalName(name)).thenReturn(Optional.empty());
        when(pc2.getNaturalName(name)).thenReturn(Optional.of(naturalName));
        when(pc2.createPattern(name)).thenReturn(expectedPattern);

        Pattern actual = patternService.getOrCreate(name);

        Assert.assertEquals(expectedPattern, actual);
        Optional<Pattern> byName = patternRepository.findByName(name);
        Assert.assertTrue(byName.isPresent());
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
}