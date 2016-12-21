package com.ignoretheextraclub.vanillasiteswap.generators;

import com.ignoretheextraclub.vanillasiteswap.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.vanillasiteswap.exceptions.NumObjectsException;
import com.ignoretheextraclub.vanillasiteswap.exceptions.StateSizeException;
import com.ignoretheextraclub.vanillasiteswap.siteswap.VanillaSiteswap;
import com.ignoretheextraclub.vanillasiteswap.state.VanillaState;
import org.junit.Test;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by caspar on 18/12/16.
 */
public class StateSearchGeneratorTest
{

    @Test
    public void test() throws Exception, InvalidSiteswapException, StateSizeException, NumObjectsException
    {
        StateSearchGenerator stateSearchGenerator = new StateSearchGenerator(500000, 7, 9, 5);
//        stateSearchGenerator.banThrow(0);
        final Collection<VanillaState[]> vanillaStates = stateSearchGenerator.generateToCollectionOfVanillaStateArray();
        final List<VanillaSiteswap> siteswaps = vanillaStates.stream().map(siteswap ->
                                                                  {
                                                                      try
                                                                      {
                                                                          return VanillaSiteswap.create(siteswap);
                                                                      }
                                                                      catch (InvalidSiteswapException e)
                                                                      {
                                                                          e.printStackTrace();
                                                                          return null;
                                                                      }
                                                                  }).collect(Collectors.toList());
        System.out.println(siteswaps);
//        Assert.assertEquals(2, siteswaps.size());

    }
}