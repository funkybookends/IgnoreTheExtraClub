package com.ignoretheextraclub.vanillasiteswap.siteswap;

import com.ignoretheextraclub.vanillasiteswap.converters.IntVanilla;
import com.ignoretheextraclub.vanillasiteswap.exceptions.*;
import com.ignoretheextraclub.vanillasiteswap.state.VanillaState;
import com.ignoretheextraclub.vanillasiteswap.state.VanillaState.VanillaStateBuilder;
import jdk.nashorn.internal.ir.annotations.Immutable;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.ignoretheextraclub.vanillasiteswap.state.VanillaState.transition;

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
public class VanillaSiteswap extends Siteswap
{
    public static final int MAX_THROW = IntVanilla.charToInt('D');

    protected final List<VanillaState> states; //The list of states, ordered, and possibly sorted
    protected final boolean prime; //A pattern is prime if it does not revisit a state twice. If it does this implies it can be decomposed into two or more siteswaps TODO determine decompositions
    protected final boolean grounded; //A pattern is grounded if it goes through the ground state, the ground state is the lowest level of excitiation
    protected final int highestThrow;
    protected final boolean sorted;
    protected final String stringSiteswap;
    protected final int[] intSiteswap;
    protected final int[] startingObjectsPerHand;

    protected VanillaSiteswap(final int numJugglers,
                              final int period,
                              final int numObjects,
                              final int[] intSiteswap,
                              final List<VanillaState> states,
                              final boolean sorted,
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
        this.sorted = sorted;
        this.stringSiteswap = stringSiteswap;
        this.intSiteswap = intSiteswap;
        this.startingObjectsPerHand = startingObjectsPerHand;
    }

    public static VanillaSiteswap create(final int[] siteswap, final boolean sort) throws InvalidSiteswapException
    {
        VanillaSiteswapBuilder builder = new VanillaSiteswapBuilder(siteswap, sort);
        return builder.buildVanillaSiteswap();
    }

    public static VanillaSiteswap create(final int[] siteswap) throws InvalidSiteswapException
    {
        return create(siteswap, true);
    }

    public static VanillaSiteswap create(final String siteswap, final boolean sort) throws InvalidSiteswapException
    {
        return create(IntVanilla.stringToIntArray(siteswap), sort);
    }

    public static VanillaSiteswap create(final String siteswap) throws InvalidSiteswapException
    {
        return create(siteswap, true);
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

    public boolean isSorted()
    {
        return sorted;
    }

    public String getStringSiteswap()
    {
        return stringSiteswap;
    }

    public int[] getIntSiteswap()
    {
        return intSiteswap;
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
        protected List<VanillaState> states;
        protected boolean prime;
        protected int period;
        protected int numObjects;
        protected boolean grounded;
        protected int highestThrow;
        protected boolean sorted;
        protected String stringSiteswap;
        protected int[] intSiteswap;
        protected int[] startingObjectsPerHand;

        public VanillaSiteswapBuilder(final int[] vanillaSiteswap, final boolean sort, final int hands) throws
                                                                                                        InvalidSiteswapException
        {
            try
            {
                states = new LinkedList<>();
                startingObjectsPerHand = new int[hands];
                period = validatePeriod(vanillaSiteswap.length);
                numObjects = validateNumObjects((int) Arrays.stream(vanillaSiteswap).average().orElse(0));
                highestThrow = Arrays.stream(vanillaSiteswap).max().orElse(0);
                buildStatesAndHands(vanillaSiteswap);
                grounded = containsAGroundState();
                prime = !containsARepeatedState();
                if (sort) sort();
                sorted = sort;
                intSiteswap = getAllThrows();
                stringSiteswap = IntVanilla.intArrayToString(intSiteswap);
            }
            catch (final BadThrowException | NumObjectsException | StateSizeException | NoTransitionException | PeriodException cause)
            {
                throw new InvalidSiteswapException("Invalid Siteswap [" + IntVanilla.intArrayToString(vanillaSiteswap) + "]", cause);
            }

            if (states.size() != period)
            {
                throw new RuntimeException("This shouldn't happen. Built states.size() != vanillaSiteswap.length");
            }
        }

        public VanillaSiteswapBuilder(final int[] vanillaSiteswap, final boolean sort) throws InvalidSiteswapException
        {
            this(vanillaSiteswap, sort, 2);
        }

        private int[] getAllThrows() throws NoTransitionException
        {
            final int[] thros = new int[period];
            for (int i = 0; i < period - 1; i++)
            {
                thros[i] = transition(states.get(i), states.get(i+1));
            }
            thros[period - 1] = transition(states.get(period - 1), states.get(0));
            return thros;
        }

        private boolean containsARepeatedState()
        {
            Set<VanillaState> stateSet = states.stream().collect(Collectors.toSet());
            return stateSet.size() != states.size();
        }

        private void buildStatesAndHands(int[] vanillaSiteswap) throws NumObjectsException,
                                                                       BadThrowException,
                                                                       StateSizeException,
                                                                       InvalidSiteswapException
        {
            final VanillaStateBuilder builder = new VanillaStateBuilder(this.highestThrow, numObjects);
            int index = 0;
            int givenObjects = 0;
            while (builder.getGivenObjects() < numObjects || index % period != 0)
            {
                builder.thenThrow(vanillaSiteswap[index % period]);
                if (givenObjects < builder.getGivenObjects())
                {
                    givenObjects++;
                    startingObjectsPerHand[index % startingObjectsPerHand.length]++;
                }
                index++;
            }

            VanillaState state = builder.build();
            states.add(state);

            for (int pos = 0; pos < vanillaSiteswap.length - 1; pos++)
            {
                state = state.thro(vanillaSiteswap[pos]);
                states.add(state);
            }
            if (Arrays.stream(startingObjectsPerHand).sum() != states.get(0).getNumObjects())
            {
                throw new RuntimeException("Did not calculate the number of objects in each hand correctly. " +
                                                   "sum(" + startingObjectsPerHand+ ") != " + states.get(0).getNumObjects());
            }
            if (!state.thro(vanillaSiteswap[period - 1]).equals(states.get(0)))
            {
                throw new InvalidSiteswapException("Did not return to original state. " +
                                                           "Final state: [" + states.get(period-1) + "], original state: [" + states.get(0) + "]");
            }
        }

        /**
         * Sorts the siteswap, finds the groundiest first throw and makes that the first throw.
         */
        private void sort()
        {
            List<Integer> scores = IntStream.range(0, period)
                    .map(this::scoreRotation)
                    .boxed()
                    .collect(Collectors.toList());

            int minPosition = IntStream.range(0, period)
                    .reduce((bestP, candP) -> scores.get(bestP) < scores.get(candP) ? bestP : candP)
                    .getAsInt();

            while (minPosition > 0)
            {
                states.add(states.remove(0));
                minPosition--;
            }
        }

        private int scoreRotation(int start)
        {
            int score = 0;
            for (int i = 0; i < period; i++)
            {
                int stateNumber = (start + i) % period;
                VanillaState state = states.get(stateNumber);
                int excitedness = state.excitedness();
                score += excitedness*(-i+1);
            }
            return score;
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
            return new VanillaSiteswap(1, period, numObjects, intSiteswap, states,sorted, prime, grounded,
                                       highestThrow, startingObjectsPerHand, stringSiteswap);
        }
    }

}
