package com.ignoretheextraclub.vanillasiteswap.siteswap;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ignoretheextraclub.vanillasiteswap.converters.IntVanilla;
import com.ignoretheextraclub.vanillasiteswap.exceptions.*;
import com.ignoretheextraclub.vanillasiteswap.sorters.SortingStrategy;
import com.ignoretheextraclub.vanillasiteswap.state.VanillaState;
import com.ignoretheextraclub.vanillasiteswap.state.VanillaState.VanillaStateBuilder;
import jdk.nashorn.internal.ir.annotations.Immutable;
import org.apache.commons.lang.NotImplementedException;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * An immutable Vanilla Siteswap.
 *
 * Defines the public interface as four methods to create, taking either an int[] or a String, and optional boolean to
 * toggle sorting.
 *
 * Other public methods access the properties of the siteswap.
 *
 * Created by caspar on 26/11/16.
 */
@Immutable
public class VanillaSiteswap extends AbstractSiteswap
{
    public static final int MAX_THROW = IntVanilla.charToInt('D');
    public static final SortingStrategy DEFAULT_SORTING_STRATEGY = SortingStrategy.HIGHEST_THROW_FIRST_STRATEGY;

    protected final VanillaState[] states; //The list of states, ordered, and possibly sorted
    protected final boolean prime; //A pattern is prime if it does not revisit a state twice. If it does this implies it can be decomposed into two or more siteswaps TODO determine decompositions
    protected final boolean grounded; //A pattern is grounded if it goes through the ground state, the ground state is the lowest level of excitiation
    @JsonProperty("highest_throw") protected final int highestThrow;
    @JsonProperty("sorting_strategy") protected final SortingStrategy sortingStrategy;
    @JsonProperty("string_siteswap") protected final String stringSiteswap;
    @JsonProperty("int_siteswap") protected final int[] intSiteswap;
    @JsonProperty("starting_objects_per_hand") protected final int[] startingObjectsPerHand;

    protected VanillaSiteswap(final int numJugglers,
                              final int period,
                              final int numObjects,
                              final int[] intSiteswap,
                              final VanillaState[] states,
                              final SortingStrategy sortingStrategy,
                              final boolean prime,
                              final boolean grounded,
                              final int highestThrow,
                              final int[] startingObjectsPerHand,
                              final String stringSiteswap) throws InvalidSiteswapException
    {
        super(numJugglers,
              period,
              numObjects);
        this.states = states;
        this.prime = prime;
        this.grounded = grounded;
        this.highestThrow = highestThrow;
        this.sortingStrategy = sortingStrategy;
        this.stringSiteswap = stringSiteswap;
        this.intSiteswap = intSiteswap;
        this.startingObjectsPerHand = startingObjectsPerHand;
    }

    public static VanillaSiteswap create(final int[] siteswap, final SortingStrategy sortingStrategy) throws InvalidSiteswapException
    {
        VanillaSiteswapBuilder builder = new VanillaSiteswapBuilder(siteswap, sortingStrategy);
        return builder.buildVanillaSiteswap();
    }

    public static VanillaSiteswap create(final int[] siteswap) throws InvalidSiteswapException
    {
        return create(siteswap, DEFAULT_SORTING_STRATEGY);
    }

    public static VanillaSiteswap create(final String siteswap, final SortingStrategy sort) throws InvalidSiteswapException
    {
        return create(IntVanilla.stringToIntArray(siteswap), sort);
    }

    public static VanillaSiteswap create(final String siteswap) throws InvalidSiteswapException
    {
        return create(siteswap, DEFAULT_SORTING_STRATEGY);
    }

    public boolean isPrime()
    {
        return prime;
    }

    public boolean isGrounded()
    {
        return grounded;
    }

    public int getHighestThrow()
    {
        return highestThrow;
    }

    public SortingStrategy getSortingStrategy()
    {
        return sortingStrategy;
    }

    public String getStringSiteswap()
    {
        return stringSiteswap;
    }

    public int[] getIntSiteswap()
    {
        return intSiteswap;
    }

    public VanillaState[] getStates()
    {
        return states;
    }

    public int[] getStartingObjectsPerHand()
    {
        return startingObjectsPerHand;
    }

    public int getStartingObjectsForHand(final int hand)
    {
        try
        {
            return startingObjectsPerHand[hand];
        }
        catch (final IndexOutOfBoundsException e)
        {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public String toString()
    {
        return this.stringSiteswap;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VanillaSiteswap that = (VanillaSiteswap) o;

        return stringSiteswap.equals(that.stringSiteswap);
    }

    @Override
    public int hashCode()
    {
        return stringSiteswap.hashCode();
    }

    /**
     * A builder class that calculates all the values.
     */
    protected static class VanillaSiteswapBuilder
    {
        protected VanillaState[] states;
        protected boolean prime;
        protected int period;
        protected int numObjects;
        protected boolean grounded;
        protected int highestThrow;
        protected SortingStrategy sortingStrategy;
        protected String stringSiteswap;
        protected int[] intSiteswap;
        protected int[] startingObjectsPerHand;

        public VanillaSiteswapBuilder(final int[] vanillaSiteswap,
                                      final SortingStrategy sortingStrategy,
                                      final int numHands) throws InvalidSiteswapException
        {
            try
            {
                states = new VanillaState[vanillaSiteswap.length];
                period = validatePeriod(vanillaSiteswap.length);
                numObjects = validateNumObjects((int) Arrays.stream(vanillaSiteswap).average().orElse(0));
                highestThrow = Arrays.stream(vanillaSiteswap).max().orElse(0);
                buildStates(vanillaSiteswap);
                grounded = containsAGroundState();
                prime = !containsARepeatedState();
                sort(sortingStrategy);
                intSiteswap = rebuildThrows();
                setStartingObjectPerHand(numHands);
                stringSiteswap = IntVanilla.intArrayToString(intSiteswap);
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

        private void setStartingObjectPerHand(int numHands) throws
                                                             StateSizeException,
                                                             NumObjectsException,
                                                             BadThrowException
        {
            startingObjectsPerHand = new int[numHands];
            final VanillaStateBuilder builder = new VanillaStateBuilder(highestThrow, numObjects);
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
                                                   "sum(" + startingObjectsPerHand + ") != " + states[0].getNumObjects());
            }
        }

        public VanillaSiteswapBuilder(final int[] vanillaSiteswap, final SortingStrategy sort) throws InvalidSiteswapException
        {
            this(vanillaSiteswap, sort, 2);
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

        private void buildStates(int[] vanillaSiteswap) throws NumObjectsException,
                                                               BadThrowException,
                                                               StateSizeException,
                                                               InvalidSiteswapException
        {
            final VanillaStateBuilder builder = new VanillaStateBuilder(this.highestThrow, numObjects);
            int index = 0;
            while (builder.getGivenObjects() < numObjects || index % period != 0)
            {
                builder.thenThrow(vanillaSiteswap[index % period]);
                index++;
            }

            VanillaState state = builder.build();
            states[0] = state;

            for (int pos = 1; pos < vanillaSiteswap.length; pos++)
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
         * Sorts the siteswap, finds the groundiest first throw and makes that the first throw.
         * @param sortingStrategy
         */
        private void sort(SortingStrategy sortingStrategy)
        {
            int newZeroIndexPosition = 0;
            try
            {
                newZeroIndexPosition = sortingStrategy.get().sort(states);
                this.sortingStrategy = sortingStrategy;
            }
            catch (final NotImplementedException | InvalidSiteswapException e)
            {
                this.sortingStrategy = SortingStrategy.NO_SORTING_STRATEGY;
            }

            if (newZeroIndexPosition != 0 )
            {
                VanillaState[] sortedStates = new VanillaState[states.length];

                System.arraycopy(states, newZeroIndexPosition, sortedStates, 0, states.length - newZeroIndexPosition);
                System.arraycopy(states, 0, sortedStates, states.length - newZeroIndexPosition, newZeroIndexPosition);

                states = sortedStates;
            }
        }

        private boolean containsAGroundState()
        {
            for (VanillaState state : states) if (state.isGround()) return true;
            return false;
        }

        private VanillaSiteswap buildVanillaSiteswap() throws InvalidSiteswapException
        {
            if (startingObjectsPerHand.length > 2)
            {
                throw new InvalidSiteswapException("No humans have more than two hands!");
            }
            return new VanillaSiteswap(1, period, numObjects, intSiteswap, states, sortingStrategy, prime, grounded,
                                       highestThrow, startingObjectsPerHand, stringSiteswap);
        }
    }

}
