package com.ignoretheextraclub.vanillasiteswap.sorters;

import com.ignoretheextraclub.vanillasiteswap.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.vanillasiteswap.state.VanillaState;
import org.apache.commons.lang.NotImplementedException;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * Created by caspar on 12/12/16.
 */
public class AbstractSortingStrategyTest
{
    private AbstractSortingStrategy sortingStrategy = new NoSortingStrategy();

    @Test
    public void getFirstMinIndex() throws Exception
    {
        Assert.assertEquals(2, sortingStrategy.getFirstMinIndex(new int[]{5,5,3,7}));
        Assert.assertEquals(0, sortingStrategy.getFirstMinIndex(new int[]{3,7}));
        Assert.assertEquals(4, sortingStrategy.getFirstMinIndex(new int[]{5,5,3,7,1}));
        Assert.assertEquals(3, sortingStrategy.getFirstMinIndex(new int[]{5,8,5,3,3}));
    }

    @Test
    public void getFirstMinIndex1() throws Exception
    {
        Assert.assertEquals(2, sortingStrategy.getFirstMinIndex(Arrays.stream(new int[]{5,5,3,7})
                                            .boxed().collect(Collectors.toList())));

        Assert.assertEquals(0, sortingStrategy.getFirstMinIndex(Arrays.stream(new int[]{3,7})
                                            .boxed().collect(Collectors.toList())));

        Assert.assertEquals(4, sortingStrategy.getFirstMinIndex(Arrays.stream(new int[]{5,5,3,7,1})
                                            .boxed().collect(Collectors.toList())));

        Assert.assertEquals(3, sortingStrategy.getFirstMinIndex(Arrays.stream(new int[]{5,8,5,3,3})
                                            .boxed().collect(Collectors.toList())));
    }

    @Test
    public void getFirstMaxIndex() throws Exception
    {

    }

    @Test
    public void getFirstMaxIndex1() throws Exception
    {

    }

}