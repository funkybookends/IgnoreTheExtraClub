package com.ignoretheextraclub.vanillasiteswap.siteswap;

import com.ignoretheextraclub.vanillasiteswap.converters.IntVanilla;
import com.ignoretheextraclub.vanillasiteswap.exceptions.*;
import com.ignoretheextraclub.vanillasiteswap.sorters.SortingUtils;
import com.ignoretheextraclub.vanillasiteswap.sorters.VanillaStateSorter;
import com.ignoretheextraclub.vanillasiteswap.state.VanillaState;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A builder class that calculates all the values.
 */
public class VanillaSiteswapBuilder
{
    protected VanillaState[] states;
    protected boolean prime;
    protected int period;
    protected int numObjects;
    protected boolean grounded;
    protected int highestThrow;
    protected String stringSiteswap;
    protected int[] intSiteswap;
    protected int[] startingObjectsPerHand;
    protected VanillaStateSorter sortingStrategy;

    protected VanillaSiteswapBuilder(final int[] vanillaSiteswap,
                                     final int numHands,
                                     final VanillaStateSorter sortingStrategy) throws InvalidSiteswapException
    {
        try
        {
            buildStates(SortingUtils.reduce(vanillaSiteswap));
            this.intSiteswap = rebuildThrows();
            postStatesInit(numHands, sortingStrategy);
        }
        catch (final BadThrowException | NumObjectsException | StateSizeException | NoTransitionException | PeriodException cause)
        {
            throw new InvalidSiteswapException("Invalid Siteswap [" + IntVanilla.intArrayToString(vanillaSiteswap) + "]", cause);
        }

        if (states.length != period || intSiteswap.length != period)
        {
            throw new RuntimeException("This shouldn't happen. Built states.size() != vanillaSiteswap.length");
        }
    }

    public VanillaSiteswapBuilder(final VanillaState[] states,
                                  final int numHands,
                                  final VanillaStateSorter sortingStrategy) throws InvalidSiteswapException
    {
        try
        {
            this.states = SortingUtils.reduce(states);
            //init things build states does
            this.period = AbstractSiteswap.validatePeriod(this.states.length);
            this.numObjects = AbstractSiteswap.validateNumObjects(this.states[0].getNumObjects());
            this.intSiteswap = rebuildThrows();
            this.highestThrow = VanillaSiteswap.validateHighestThrow(intSiteswap);
            postStatesInit(numHands, sortingStrategy);
        }
        catch (final BadThrowException | NumObjectsException | StateSizeException | NoTransitionException | PeriodException cause)
        {
            throw new InvalidSiteswapException("Invalid Siteswap [" + Arrays.toString(states) + "]", cause);
        }

        if (this.states.length != period || intSiteswap.length != period)
        {
            throw new RuntimeException("This shouldn't happen. Built states.size() != vanillaSiteswap.length");
        }
    }

    private void postStatesInit(int numHands, VanillaStateSorter sortingStrategy) throws
                                                                                     StateSizeException,
                                                                                     NumObjectsException,
                                                                                     BadThrowException,
                                                                                     NoTransitionException,
                                                                                     InvalidSiteswapException
    {
        sort(sortingStrategy);
        this.prime = !containsARepeatedState();
        this.grounded = containsAGroundState();
        setStartingObjectPerHand(numHands);
        this.stringSiteswap = IntVanilla.intArrayToString(intSiteswap);
    }

    private void setStartingObjectPerHand(int numHands) throws
                                                         StateSizeException,
                                                         NumObjectsException,
                                                         BadThrowException
    {
        startingObjectsPerHand = new int[numHands];
        final VanillaState.VanillaStateBuilder builder = new VanillaState.VanillaStateBuilder(highestThrow, numObjects);
        int createdObjects = 0;
        for (int i = 0; createdObjects < numObjects; i++)
        {
            builder.thenThrow(intSiteswap[i % period]);
            if (builder.getGivenObjects() > createdObjects)
            {
                createdObjects++;
                startingObjectsPerHand[i % numHands]++;
            }
        }
        if (Arrays.stream(startingObjectsPerHand).sum() != states[0].getNumObjects())
        {
            throw new RuntimeException("Did not calculate the number of objects in each hand correctly. " +
                   "sum(" + Arrays.toString(startingObjectsPerHand) + ") != " + states[0].getNumObjects());
        }
    }

    private int[] rebuildThrows() throws NoTransitionException
    {
        final int[] thros = new int[period];
        for (int i = 0; i < period; i++)
        {
            thros[i] = VanillaState.transition(states[i], states[(i + 1) % states.length]);
        }
        return thros;
    }

    private boolean containsARepeatedState()
    {
        Set<VanillaState> stateSet = Arrays.stream(states).collect(Collectors.toSet());
        return stateSet.size() != states.length;
    }

    private void buildStates(int[] vanillaSiteswap)
        throws NumObjectsException,
               BadThrowException,
               StateSizeException,
               InvalidSiteswapException,
               PeriodException
    {
        this.states = new VanillaState[vanillaSiteswap.length];
        this.period = AbstractSiteswap.validatePeriod(vanillaSiteswap.length);
        this.numObjects = AbstractSiteswap.validateNumObjects((int) Arrays.stream(vanillaSiteswap).average().orElse(0));
        this.highestThrow = VanillaSiteswap.validateHighestThrow(vanillaSiteswap);

        final VanillaState.VanillaStateBuilder builder = new VanillaState.VanillaStateBuilder(highestThrow, numObjects);
        int index = 0;
        while (builder.getGivenObjects() < numObjects || index % period != 0)
        {
            builder.thenThrow(vanillaSiteswap[index % period]);
            index++;
        }

        VanillaState state = builder.build();
        states[0] = state;

        for (int pos = 1; pos < period; pos++)
        {
            state = state.thro(vanillaSiteswap[pos-1]);
            states[pos] = state;
        }
        if (!state.thro(vanillaSiteswap[period - 1]).equals(states[0]))
        {
            throw new InvalidSiteswapException("Did not return to original state. " +
                                                       "Final state: [" + states[period - 1] + "], original state: [" + states[0] + "]");
        }
    }

    /**
     * Sorts the siteswap using the given strategy. If it fails then the no sorting strategy will be used.
     * @param sortingStrategy
     */
    private void sort(VanillaStateSorter sortingStrategy) throws InvalidSiteswapException
    {
            SortingUtils.Rotations<VanillaState> rotations = new SortingUtils.Rotations<>(states);
            states = rotations.sort(sortingStrategy);
            this.sortingStrategy = sortingStrategy;
    }

    private boolean containsAGroundState()
    {
        for (VanillaState state : states) if (state.isGround()) return true;
        return false;
    }

    public VanillaSiteswap buildVanillaSiteswap() throws InvalidSiteswapException
    {
        if (startingObjectsPerHand.length > 2)
        {
            throw new InvalidSiteswapException("No humans have more than two hands!" +
                      "You may mean to call another builder that doesn't ensure there is only one juggler.");
        }
        return new VanillaSiteswap(1, period, numObjects, intSiteswap, states, prime, grounded,
                                   highestThrow, startingObjectsPerHand, stringSiteswap, sortingStrategy.getName());
    }
}
