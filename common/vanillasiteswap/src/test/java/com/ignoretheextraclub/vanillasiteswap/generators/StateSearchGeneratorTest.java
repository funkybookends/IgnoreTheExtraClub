package com.ignoretheextraclub.vanillasiteswap.generators;

import com.ignoretheextraclub.vanillasiteswap.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.vanillasiteswap.exceptions.NumObjectsException;
import com.ignoretheextraclub.vanillasiteswap.exceptions.StateSizeException;
import com.ignoretheextraclub.vanillasiteswap.generators.runners.StateSearcher;
import com.ignoretheextraclub.vanillasiteswap.siteswap.VanillaSiteswap;
import com.ignoretheextraclub.vanillasiteswap.state.VanillaState;
import org.junit.Assert;
import org.junit.Test;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Created by caspar on 18/12/16.
 */
public class StateSearchGeneratorTest
{

    @Test
    public void test() throws Exception, InvalidSiteswapException, StateSizeException, NumObjectsException
    {
        StateSearchGenerator stateSearchGenerator = new StateSearchGenerator(500000, 5, 12, 5);
        Collection<VanillaState[]> vanillaStates = stateSearchGenerator.generateToCollectionOfVanillaStateArray();
        Assert.assertEquals(93, vanillaStates.size());


        stateSearchGenerator = new StateSearchGenerator(500000, 5, 12, 5).banThrow(5);
        vanillaStates = stateSearchGenerator.generateToCollectionOfVanillaStateArray();
//        print(vanillaStates);
        Assert.assertEquals(42, vanillaStates.size());

        stateSearchGenerator = new StateSearchGenerator(500000, 5, 12, 5).banThrow(5).banThrow(3);
        vanillaStates = stateSearchGenerator.generateToCollectionOfVanillaStateArray();
//        print(vanillaStates);
        Assert.assertEquals(19, vanillaStates.size());
    }

    @Test
    public void bigTest() throws Exception, InvalidSiteswapException, StateSizeException, NumObjectsException
    {
        StateSearchGenerator stateSearchGenerator = new StateSearchGenerator(Integer.MAX_VALUE, 15, 12, 5);
        Collection<VanillaState[]> results = stateSearchGenerator.generateToCollectionOfVanillaStateArray();

        try (PrintWriter out = new PrintWriter("p15m12o5.results"))
        {
            results.stream().map(result -> {
                try
                {
                    return VanillaSiteswap.create(result);
                }
                catch (InvalidSiteswapException e)
                {
                    return null;
                }
            }).forEach(s -> out.println(s.toString()));
        }
//        print(results);
        System.out.println(results.size());
    }

    private void print(Collection<VanillaState[]> results)
    {
        System.out.println(Arrays.toString(results.stream().map(vanillaState ->
                                                                {
                                                                    try
                                                                    {
                                                                        return VanillaSiteswap.create(vanillaState);
                                                                    }
                                                                    catch (InvalidSiteswapException e)
                                                                    {
                                                                        e.printStackTrace();
                                                                        return null;
                                                                    }
                                                                }).collect(Collectors.toList()).toArray()));
    }

}