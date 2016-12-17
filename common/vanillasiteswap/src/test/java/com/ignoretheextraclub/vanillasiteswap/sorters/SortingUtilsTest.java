package com.ignoretheextraclub.vanillasiteswap.sorters;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Created by caspar on 12/12/16.
 */
public class SortingUtilsTest
{
    @Test
    public void getFirstMinIndex_int() throws Exception
    {
        Assert.assertEquals(2, SortingUtils.getFirstMinIndex(new int[]{5,5,3,7}));
        Assert.assertEquals(0, SortingUtils.getFirstMinIndex(new int[]{3,7}));
        Assert.assertEquals(4, SortingUtils.getFirstMinIndex(new int[]{5,5,3,7,1}));
        Assert.assertEquals(3, SortingUtils.getFirstMinIndex(new int[]{5,8,5,3,3}));
    }

    @Test
    public void getFirstMinIndex_Object() throws Exception
    {
        Assert.assertEquals(2, SortingUtils.getFirstMinIndex(Arrays.stream(new int[]{5,5,3,7}).boxed().collect(Collectors.toList())));
        Assert.assertEquals(0, SortingUtils.getFirstMinIndex(Arrays.stream(new int[]{3,7}).boxed().collect(Collectors.toList())));
        Assert.assertEquals(4, SortingUtils.getFirstMinIndex(Arrays.stream(new int[]{5,5,3,7,1}).boxed().collect(Collectors.toList())));
        Assert.assertEquals(3, SortingUtils.getFirstMinIndex(Arrays.stream(new int[]{5,8,5,3,3}).boxed().collect(Collectors.toList())));
    }

    @Test
    public void getFirstMaxIndex_Object() throws Exception
    {
        Assert.assertEquals(3, SortingUtils.getFirstMaxIndex(Arrays.stream(new int[]{5,5,3,7}).boxed().collect(Collectors.toList())));
        Assert.assertEquals(1, SortingUtils.getFirstMaxIndex(Arrays.stream(new int[]{3,7}).boxed().collect(Collectors.toList())));
        Assert.assertEquals(3, SortingUtils.getFirstMaxIndex(Arrays.stream(new int[]{5,5,3,7,1}).boxed().collect(Collectors.toList())));
        Assert.assertEquals(1, SortingUtils.getFirstMaxIndex(Arrays.stream(new int[]{5,8,5,3,3}).boxed().collect(Collectors.toList())));
    }

    @Test
    public void getFirstMaxIndex_int() throws Exception
    {
        Assert.assertEquals(3, SortingUtils.getFirstMaxIndex(new int[]{5,5,3,7}));
        Assert.assertEquals(1, SortingUtils.getFirstMaxIndex(new int[]{3,7}));
        Assert.assertEquals(3, SortingUtils.getFirstMaxIndex(new int[]{5,5,3,7,1}));
        Assert.assertEquals(1, SortingUtils.getFirstMaxIndex(new int[]{5,8,5,3,3}));
    }

}