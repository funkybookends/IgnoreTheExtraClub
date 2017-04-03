package com.ignoretheextraclub.service.impl;

import com.codahale.metrics.MetricRegistry;
import com.ignoretheextraclub.model.data.Pattern;
import com.ignoretheextraclub.model.data.PatternName;
import com.ignoretheextraclub.model.data.Activity;
import com.ignoretheextraclub.persistence.PatternRepository;
import com.ignoretheextraclub.service.activity.ActivityService;
import com.ignoretheextraclub.service.pattern.PatternService;
import com.ignoretheextraclub.service.pattern.PatternServiceImpl;
import com.ignoretheextraclub.service.pattern.constructors.PatternConstructor;
import com.ignoretheextraclub.service.pattern.constructors.FourHandedSiteswapPatternConstructor;
import com.ignoretheextraclub.service.pattern.constructors.TwoHandedSiteswapPatternConstructor;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.FourHandedSiteswap;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by caspar on 08/03/17.
 */
public class PatternServiceImplTest
{
    // Service under test
    private PatternService patternService;

    // Mocks
    private PatternRepository patternRepository;

    // Spys
    private PatternConstructor pc1;
    private PatternConstructor pc2;
    private ActivityService activityService;

    @Before
    public void setUp() throws Exception
    {
        pc1 = spy(new FourHandedSiteswapPatternConstructor());
        pc2 = spy(new TwoHandedSiteswapPatternConstructor());

        patternRepository = mock(PatternRepository.class);
        activityService = mock(ActivityService.class);
        MetricRegistry metricRegistry = new MetricRegistry();

        patternService = new PatternServiceImpl(patternRepository,
                activityService,
                Arrays.asList(pc1, pc2), metricRegistry);
        patternRepository.deleteAll();
    }

    @Test
    public void GIVEN_noPattern_EXPECT_empty() throws Exception
    {
        when(patternRepository.findByName("any name")).thenReturn(Optional.empty());
        assertThat(patternService.get("any name")).isNotPresent();
    }

    @Test
    public void GIVEN_pattern_EXPECT_pattern() throws Exception
    {
        final String name = "name";
        final Pattern pattern = new Pattern(FourHandedSiteswap.create("975"), new PatternName(name, 0));

        when(patternRepository.findByName(name)).thenReturn(Optional.of(pattern));
        assertThat(patternService.get(name)).isPresent();
        verify(patternRepository, times(1)).findByName(name);
    }

    @Test
    public void GIVEN_pc1CantConstruct_EXPECT_pc2IsCalled_AND_patternIsSaved() throws Exception
    {
        String name = "345";

        when(patternRepository.findByName(name)).thenReturn(Optional.empty());
        when(patternRepository.findByName("534")).thenReturn(Optional.empty());
        when(patternRepository.save(any(Pattern.class))).then(returnsFirstArg());

        final Pattern actual = patternService.getOrCreate(name);

        verify(pc1, times(1)).getNaturalName(name);
        verify(pc1, times(0)).createPattern(name);
        verify(pc2, times(1)).getNaturalName(name);
        verify(pc2, times(1)).createPattern(name);

        verify(patternRepository, times(1)).save(actual);
        verify(activityService, times(1)).recordNewPattern(actual);
    }

    @Test
    public void GIVEN_save_EXPECT_saved() throws Exception
    {
        when(patternRepository.save(any(Pattern.class))).then(returnsFirstArg());

        final Pattern pattern = new Pattern(FourHandedSiteswap.create("975"), new PatternName("name", 0));

        final Pattern save = patternService.save(pattern);

        verify(patternRepository, times(1)).save(pattern);
        assertThat(save).isEqualTo(pattern);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void GIVEN_newerPatterns_EXPECT_pagesCorrectly() throws Exception
    {
        final Page mockPage = mock(Page.class);

        final ArgumentCaptor<PageRequest> pageRequestArgumentCaptor = ArgumentCaptor.forClass(PageRequest.class);

        when(patternRepository.findAll(any(PageRequest.class))).thenReturn(mockPage);

        final int page = 1;
        final int size = 2;

        Page<Pattern> newest = patternService.newest(page, size);

        verify(patternRepository, times(1)).findAll(pageRequestArgumentCaptor.capture());

        final PageRequest pageRequest = pageRequestArgumentCaptor.getValue();

        assertThat(pageRequest).hasFieldOrPropertyWithValue("page", page);
        assertThat(pageRequest).hasFieldOrPropertyWithValue("size", size);
        final Sort sort = pageRequest.getSort();
        assertThat(sort.getOrderFor("createdDate").getDirection()).isEqualTo(Sort.Direction.DESC);
        assertThat(newest).isEqualTo(mockPage);
    }
}