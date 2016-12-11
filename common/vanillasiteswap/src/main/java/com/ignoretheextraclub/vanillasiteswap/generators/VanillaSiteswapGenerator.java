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
    private int numObjects;

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
        if (grounded) startState = VanillaState.getGroundState(maxThrow, numObjects);
        this.resultLimit = resultLimit;
        this.grounded = grounded;
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
        if (startState.getNumObjects() != numObjects) throw new IllegalArgumentException("Your state does not have the same number of objects as the stated number of objects");
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

    private void generate()
    {
        throw new NotImplementedException();
    }

    public List<VanillaState[]> generateToListOfVanillaStateArray()
    {
        if (results == null) generate();
        return results;
    }

    public List<VanillaSiteswap> generateToListOfVanillaSiteswap()
    {
        if (results == null) generate();

        throw new NotImplementedException();
    }

    public List<String> generateToListOfStringSiteswaps()
    {
        if (results == null) generate();

        throw new NotImplementedException();
    }
}
