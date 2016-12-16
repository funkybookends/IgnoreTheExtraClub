package com.ignoretheextraclub.vanillasiteswap.generators;

import com.ignoretheextraclub.vanillasiteswap.converters.IntVanilla;
import com.ignoretheextraclub.vanillasiteswap.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.vanillasiteswap.exceptions.NoTransitionException;
import com.ignoretheextraclub.vanillasiteswap.exceptions.NumObjectsException;
import com.ignoretheextraclub.vanillasiteswap.exceptions.StateSizeException;
import com.ignoretheextraclub.vanillasiteswap.siteswap.VanillaSiteswap;
import com.ignoretheextraclub.vanillasiteswap.siteswap.VanillaSiteswapBuilder;
import com.ignoretheextraclub.vanillasiteswap.state.VanillaState;
import org.apache.commons.lang.NotImplementedException;

import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * Created by caspar on 11/12/16.
 */
public class VanillaSiteswapGenerator
{
    private int numHands;
    private boolean[] legalThrow;
    private int finalPeriod;
    private boolean prime;
    private boolean grounded;
    private int numJugglers = 1;
    private Set<VanillaState> bannedStates = new TreeSet<>();
    private int resultLimit;
    private int numObjects;

    private Set<VanillaState[]> results;
    private Set<VanillaSiteswap> resultsVanillaSiteswap;
    private Set<String> resultsString;

    public VanillaSiteswapGenerator(final int numObjects,
                                    final int maxThrow,
                                    final boolean prime,
                                    final boolean grounded,
                                    final int finalPeriod,
                                    final int resultLimit)
    throws StateSizeException, NumObjectsException
    {
        this.legalThrow = new boolean[maxThrow];
        this.finalPeriod = finalPeriod;
        this.numObjects = numObjects;
        for (int i = 0; i < legalThrow.length; i++) legalThrow[i] = true;
        this.prime = prime;
        this.resultLimit = resultLimit;
        this.grounded = grounded;
        this.numJugglers = 1;
        this.numHands = 2;
    }
    
    public VanillaSiteswapGenerator setNumJugglers(final int numJugglers)
    {
        if (results != null) throw new UnsupportedOperationException("Already Generated");
        this.numJugglers = numJugglers;
        return this;
    }

    public VanillaSiteswapGenerator setNumHands(final int numHands)
    {
        if (results != null) throw new UnsupportedOperationException("Already Generated");
        this.numHands = numHands;
        return this;
    }

    public VanillaSiteswapGenerator banThrows(int... thros)
    {
        if (results != null) throw new UnsupportedOperationException("Already Generated");
        for (int thro : thros) banThrow(thro);
        return this;
    }

    public VanillaSiteswapGenerator banThrows(Set<Integer> thros)
    {
        if (results != null) throw new UnsupportedOperationException("Already Generated");
        for (int thro : thros) banThrow(thro);
        return this;
    }

    public VanillaSiteswapGenerator banThrow(int thro)
    {
        if (results != null) throw new UnsupportedOperationException("Already Generated");
        if (thro > legalThrow.length && thro >= 0) legalThrow[thro] = false;
        return this;
    }

    public VanillaSiteswapGenerator banState(VanillaState bannedState)
    {
        if (results != null) throw new UnsupportedOperationException("Already Generated");
        this.bannedStates.add(bannedState);
        return this;
    }

    public int getNumResults()
    {
        if (results == null) throw new UnsupportedOperationException("Must generate first!");
        return results.size();
    }

    private void generate()
    {
        throw new NotImplementedException();
    }

    public Set<VanillaState[]> generateToListOfVanillaStateArray()
    {
        if (results == null) generate();
        return results;
    }

    public Set<VanillaSiteswap> generateToSetOfVanillaSiteswap()
    {
        if (results == null) generate();
        if (resultsVanillaSiteswap == null)
        {
            results.stream().map(states -> {
                try
                {
                    final VanillaSiteswapBuilder builder = new VanillaSiteswapBuilder(states, numHands, VanillaSiteswap.DEFAULT_SORTING_STRATEGY);
                    return builder.buildVanillaSiteswap();
                }
                catch (InvalidSiteswapException e)
                {
                    throw new RuntimeException("Generated a siteswap the builder didn't like");
                }
            }).collect(Collectors.toSet());
        }
        return resultsVanillaSiteswap;
    }

    public Set<String> generateToSetOfStringSiteswaps()
    {
        if (results == null) generate();
        if (resultsString == null)
        {
            if (resultsVanillaSiteswap != null)
            {
                resultsString = resultsVanillaSiteswap.stream()
                        .map(VanillaSiteswap::toString)
                        .collect(Collectors.toSet());
            }
            else
            {
                resultsString = results.stream().map(
                        states ->
                        {
                            try
                            {
                                return IntVanilla.intArrayToString(VanillaState.convert(states));
                            }
                            catch (NoTransitionException e)
                            {
                                throw new RuntimeException("Generated a siteswap the convertor didn't like");
                            }
                        }).collect(Collectors.toSet());
            }
        }
        return resultsString;
    }
}
