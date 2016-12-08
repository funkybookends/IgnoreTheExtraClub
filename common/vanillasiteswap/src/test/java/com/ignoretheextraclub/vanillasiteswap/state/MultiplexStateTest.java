package com.ignoretheextraclub.vanillasiteswap.state;

import com.ignoretheextraclub.vanillasiteswap.exceptions.*;
import com.ignoretheextraclub.vanillasiteswap.state.MultiplexState.MultiplexStateBuilder;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by caspar on 06/12/16.
 */
public class MultiplexStateTest
{
    @Test
    public void testBuilder() throws Exception, StateSizeException, NumObjectsException, BadThrowException, OccupancyException
    {
        MultiplexState state = new MultiplexStateBuilder(2, 5, 4)
                .thenThrow(3)
                .thenThrow(3)
                .thenThrow(3)
                .thenThrow(1, 3)
                .thenThrow(1, 3)
                .thenThrow(2, 3)
                .thenThrow(3)
                .build();

        final String expected = "211__";
        Assert.assertEquals(expected, state.toString());
        Assert.assertTrue(state.canThrow());
        Assert.assertEquals(2, state.numToThrow());
    }

    @Test
    public void transition() throws Exception, StateSizeException, NumObjectsException, BadThrowException, OccupancyException, NoTransitionException
    {
        MultiplexState state211 = new MultiplexStateBuilder(2, 5, 4)
                .thenThrow(3)
                .thenThrow(3)
                .thenThrow(3)
                .thenThrow(1, 3)
                .build();


        MultiplexState state121 = new MultiplexStateBuilder(2, 5, 4)
                .thenThrow(3)
                .thenThrow(3)
                .thenThrow(3)
                .thenThrow(2, 3)
                .build();

        final int[] expected211to121 = new int[]{2, 3};
        Assert.assertArrayEquals(expected211to121, MultiplexState.transition(state211, state121));

        final int[] expected121to211 = new int[]{3};
        Assert.assertArrayEquals(expected121to211, MultiplexState.transition(state121, state211));
    }

}