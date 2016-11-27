package com.ignoretheextraclub.vanillasiteswap.state;

import com.ignoretheextraclub.vanillasiteswap.exceptions.BadThrowException;
import com.ignoretheextraclub.vanillasiteswap.exceptions.NoTransitionException;
import com.ignoretheextraclub.vanillasiteswap.exceptions.NumObjectsException;
import com.ignoretheextraclub.vanillasiteswap.exceptions.StateSizeException;
import com.ignoretheextraclub.vanillasiteswap.state.VanillaState.VanillaStateBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by caspar on 26/11/16.
 */
public class VanillaStateTest
{
    private VanillaState sB3M5ground;
    private VanillaState sB3M5topExcitation;


    @Before
    public void setUp() throws Exception, NumObjectsException, BadThrowException, StateSizeException
    {
        sB3M5ground = new VanillaStateBuilder(5, 3).thenThrow(3).thenThrow(3).thenThrow(3).build();
        sB3M5topExcitation = new VanillaStateBuilder(5, 3).thenThrow(5).thenThrow(5).thenThrow(5).build();
    }

    @Test
    public void getAvailableThrows() throws Exception, StateSizeException, NumObjectsException, BadThrowException
    {
        Assert.assertArrayEquals("3 Ball Max Throw 5 ground available throws", new int[]{3,4,5}, sB3M5ground.getAvailableThrows());
        Assert.assertArrayEquals("3 Ball Max Throw 5 highly excited available throws", new int[]{0}, sB3M5topExcitation.getAvailableThrows());
        Assert.assertArrayEquals("3 Ball Max Throw 5 highly excited available throws 1 drop", new int[]{0}, sB3M5topExcitation.thro(0).getAvailableThrows());
    }

    @Test
    public void getMaxThrow()
    {
        Assert.assertEquals(5, sB3M5ground.getMaxThrow());
        Assert.assertEquals(5, sB3M5topExcitation.getMaxThrow());
    }

    @Test
    public void getNumObjects()
    {
        Assert.assertEquals(3, sB3M5ground.getNumObjects());
        Assert.assertEquals(3, sB3M5topExcitation.getNumObjects());
    }

    @Test
    public void isGround()
    {
        Assert.assertEquals(true, sB3M5ground.isGround());
        Assert.assertEquals(false, sB3M5topExcitation.isGround());
    }

    @Test
    public void excitedness() throws NumObjectsException, BadThrowException, StateSizeException
    {
        //           1248E
        VanillaState xxxoo_0 = new VanillaStateBuilder(5,3).thenThrow(3).thenThrow(3).thenThrow(3).build(); //7
        VanillaState xxoxo_1 = new VanillaStateBuilder(5,3).thenThrow(3).thenThrow(3).thenThrow(4).build(); //11
        VanillaState xoxxo_2 = new VanillaStateBuilder(5,3).thenThrow(3).thenThrow(4).thenThrow(4).build(); //13
        VanillaState oxxxo_3 = new VanillaStateBuilder(5,3).thenThrow(4).thenThrow(4).thenThrow(4).build(); //
        VanillaState xxoox_4 = new VanillaStateBuilder(5,3).thenThrow(3).thenThrow(3).thenThrow(5).build();
        VanillaState xoxox_5 = new VanillaStateBuilder(5,3).thenThrow(3).thenThrow(4).thenThrow(5).build();
        VanillaState xooxx_6 = new VanillaStateBuilder(5,3).thenThrow(3).thenThrow(5).thenThrow(5).build();
        VanillaState oxoxx_7 = new VanillaStateBuilder(5,3).thenThrow(4).thenThrow(5).thenThrow(5).build();
        VanillaState ooxxx_8 = new VanillaStateBuilder(5,3).thenThrow(5).thenThrow(5).thenThrow(5).build();

        List<VanillaState> statesSorted = new LinkedList<>();
        statesSorted.add(xxoxo_1);
        statesSorted.add(xoxxo_2);
        statesSorted.add(oxxxo_3);
        statesSorted.add(xxoox_4);
        statesSorted.add(xoxox_5);
        statesSorted.add(xooxx_6);
        statesSorted.add(oxoxx_7);
        statesSorted.add(ooxxx_8);

        for (int i = 0; i < statesSorted.size(); i++)
        {
            for (int j = i; j < statesSorted.size(); j++)
            {
                if (i==j)
                {
                    Assert.assertTrue("i[" + statesSorted.get(i) + "][" + i + "] == j[" + statesSorted.get(j) + "][" + j + "]",
                                               statesSorted.get(i).excitedness() == statesSorted.get(j).excitedness());
                }
                else
                {
                    Assert.assertTrue("i[" + statesSorted.get(i) + "][" + i + "] < j[" + statesSorted.get(j) + "][" + j + "]",
                                               statesSorted.get(i).excitedness() < statesSorted.get(j).excitedness());
                }
            }
        }
    }

    @Test
    public void thro() throws BadThrowException
    {
        Assert.assertEquals("ground throws 3 gets ground", sB3M5ground, sB3M5ground.thro(3));
    }

    @Test
    public void transition() throws BadThrowException, StateSizeException, NumObjectsException, NoTransitionException
    {
        VanillaState xoo = new VanillaStateBuilder(3, 1).thenThrow(1).build();
        VanillaState oxo = new VanillaStateBuilder(3, 1).thenThrow(2).build();
        VanillaState oox = new VanillaStateBuilder(3, 1).thenThrow(3).build();

        Assert.assertEquals(3, VanillaState.transition(xoo, oox));
        Assert.assertEquals(0, VanillaState.transition(oox, oxo));
        Assert.assertEquals(0, VanillaState.transition(oxo, xoo));
        Assert.assertEquals(1, VanillaState.transition(xoo, xoo));
        Assert.assertEquals(2, VanillaState.transition(xoo, oxo));

    }

}