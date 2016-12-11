package com.ignoretheextraclub.vanillasiteswap.generators;

import com.ignoretheextraclub.vanillasiteswap.exceptions.NumObjectsException;
import com.ignoretheextraclub.vanillasiteswap.exceptions.StateSizeException;
import com.ignoretheextraclub.vanillasiteswap.siteswap.VanillaSiteswap;
import com.ignoretheextraclub.vanillasiteswap.state.VanillaState;
import org.apache.commons.lang.NotImplementedException;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by caspar on 11/12/16.
 */
public class VanillaSiteswapGenerator
{
    private boolean[] legalThrow;
    private int finalPeriod;
    private boolean prime;
    private boolean grounded;
    private VanillaState startState;
    private int numJugglers = 1;
    private List<VanillaState> bannedStates = new LinkedList<>();
    private List<VanillaState[]> results;
    private int resultLimit;

    public VanillaSiteswapGenerator(final int numObjects,
                                    final int maxThrow,
                                    final boolean prime,
                                    final boolean grounded,
                                    final int finalPeriod,
                                    final int resultLimit)
    throws StateSizeException, NumObjectsException
    {
        this.finalPeriod = finalPeriod;
        this.legalThrow = new boolean[maxThrow];
        for (int i = 0; i < legalThrow.length; i++) legalThrow[i] = true;
        this.prime = prime;
        if (grounded) startState = VanillaState.getGroundState(maxThrow, numObjects);
        this.resultLimit = resultLimit;
    }
    
    protected VanillaSiteswapGenerator setNumJugglers(final int numJugglers)
    {
        if (results != null) throw new UnsupportedOperationException("Already Generated");
        this.numJugglers = numJugglers;
        return this;
    }

    public VanillaSiteswapGenerator banThrows(int... thros)
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

    public VanillaSiteswapGenerator setStartState(VanillaState startState)
    {
        if (results != null) throw new UnsupportedOperationException("Already Generated");
        if (grounded) throw new UnsupportedOperationException("Method not available when grounded");
        this.startState = startState;
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

    public List<VanillaState[]> generateToListOfVanillaStateArray()
    {
        throw new NotImplementedException();
    }

    public List<VanillaSiteswap> generateToListOfVanillaSiteswap()
    {
        throw new NotImplementedException();
    }

    public List<String> generateToListOfStringSiteswaps()
    {
        throw new NotImplementedException();
    }
}
