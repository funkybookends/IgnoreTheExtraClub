package com.ignoretheextraclub.vanillasiteswap.siteswap;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ignoretheextraclub.vanillasiteswap.converters.IntVanilla;
import com.ignoretheextraclub.vanillasiteswap.exceptions.*;
import com.ignoretheextraclub.vanillasiteswap.sorters.VanillaStateSorter;
import com.ignoretheextraclub.vanillasiteswap.sorters.impl.HighestThrowFirstStrategy;
import com.ignoretheextraclub.vanillasiteswap.state.VanillaState;
import com.ignoretheextraclub.vanillasiteswap.thros.VanillaThro;
import jdk.nashorn.internal.ir.annotations.Immutable;
import org.apache.commons.lang.NotImplementedException;

import java.util.Arrays;

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
public class VanillaSiteswap extends AbstractSiteswap<VanillaState, VanillaThro>
{
    public static final int MAX_THROW = IntVanilla.charToInt('D');
    public static final VanillaStateSorter DEFAULT_SORTING_STRATEGY = new HighestThrowFirstStrategy();

    protected final VanillaState[] states; //The list of states, ordered, and possibly sorted
    protected final boolean prime; //A pattern is prime if it does not revisit a state twice. If it does this implies it can be decomposed into two or more siteswaps TODO determine decompositions
    protected final boolean grounded; //A pattern is grounded if it goes through the ground state, the ground state is the lowest level of excitiation
    @JsonProperty("highest_throw") protected final int highestThrow;
    @JsonProperty("string_siteswap") protected final String stringSiteswap;
    @JsonProperty("int_siteswap") protected final int[] intSiteswap;
    @JsonProperty("starting_objects_per_hand") protected final int[] startingObjectsPerHand;
    @JsonProperty("sorting_strategy") protected final String sortingStrategyName;

    /**
     * Constructor. Package private and should only be used by the builder.
     * @param numJugglers
     * @param period
     * @param numObjects
     * @param intSiteswap
     * @param states
     * @param prime
     * @param grounded
     * @param highestThrow
     * @param startingObjectsPerHand
     * @param stringSiteswap
     * @param sortingStrategyName
     * @throws InvalidSiteswapException
     */
    @JsonCreator
    VanillaSiteswap(@JsonProperty("num_jugglers") final int numJugglers,
                    @JsonProperty("period") final int period,
                    @JsonProperty("num_objects") final int numObjects,
                    @JsonProperty("int_siteswap") final int[] intSiteswap,
                    @JsonProperty("states") final VanillaState[] states,
                    @JsonProperty("prime") final boolean prime,
                    @JsonProperty("grounded") final boolean grounded,
                    @JsonProperty("highest_throw") final int highestThrow,
                    @JsonProperty("starting_objects_per_hand") final int[] startingObjectsPerHand,
                    @JsonProperty("string_siteswap") final String stringSiteswap,
                    @JsonProperty("sorting_strategy") final String sortingStrategyName) throws InvalidSiteswapException
    {
        super(numJugglers,
              period,
              numObjects);
        this.states = states;
        this.prime = prime;
        this.grounded = grounded;
        this.highestThrow = highestThrow;
        this.stringSiteswap = stringSiteswap;
        this.intSiteswap = intSiteswap;
        this.startingObjectsPerHand = startingObjectsPerHand;
        this.sortingStrategyName = sortingStrategyName;
    }

    public static VanillaSiteswap create(final int[] siteswap,
                                         final int numHands,
                                         final VanillaStateSorter sortingStrategy)
            throws InvalidSiteswapException
    {
        VanillaSiteswapBuilder builder = new VanillaSiteswapBuilder(siteswap, numHands, sortingStrategy);
        return builder.buildVanillaSiteswap();
    }

    public static VanillaSiteswap create(final int[] siteswap,
                                         final VanillaStateSorter sortingStrategy)
    throws InvalidSiteswapException
    {
        return create(siteswap, 2, sortingStrategy);
    }

    public static VanillaSiteswap create(final int[] siteswap,
                                         final int numHands)
    throws InvalidSiteswapException
    {
        return create(siteswap, numHands, DEFAULT_SORTING_STRATEGY);
    }

    public static VanillaSiteswap create(final int[] siteswap)
        throws InvalidSiteswapException
    {
        return create(siteswap, 2, DEFAULT_SORTING_STRATEGY);
    }

    public static VanillaSiteswap create(final String stringSiteswap,
                                         final int numHands,
                                         final VanillaStateSorter sortingStrategy) throws InvalidSiteswapException
    {
        return create(IntVanilla.stringToIntArray(stringSiteswap), numHands, sortingStrategy);
    }

    public static VanillaSiteswap create(final String stringSiteswap,
                                         final VanillaStateSorter sortingStrategy) throws InvalidSiteswapException
    {
        return create(stringSiteswap, 2, sortingStrategy);
    }

    public static VanillaSiteswap create(final String stringSiteswap,
                                         final int numHands) throws InvalidSiteswapException
    {
        return create(stringSiteswap, numHands, DEFAULT_SORTING_STRATEGY);
    }

    public static VanillaSiteswap create(final String stringSiteswap) throws InvalidSiteswapException
    {
        return create(stringSiteswap, 2, DEFAULT_SORTING_STRATEGY);
    }

    public static VanillaSiteswap create(final VanillaState[] states,
                                         final int numHands,
                                         final VanillaStateSorter sortingStrategy) throws InvalidSiteswapException
    {
        VanillaSiteswapBuilder builder = new VanillaSiteswapBuilder(states, numHands, sortingStrategy);
        return builder.buildVanillaSiteswap();
    }

    public static VanillaSiteswap create(final VanillaState[] states,
                                         final VanillaStateSorter sortingStrategy) throws InvalidSiteswapException
    {
        return create(states, 2, sortingStrategy);
    }

    public static VanillaSiteswap create(final VanillaState[] states,
                                         final int numHands) throws InvalidSiteswapException
    {
        return create(states, numHands, DEFAULT_SORTING_STRATEGY);
    }

    public static VanillaSiteswap create(final VanillaState[] states) throws InvalidSiteswapException
    {
        return create(states, 2, DEFAULT_SORTING_STRATEGY);
    }

    /**
     * Getters
     */
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

    @JsonIgnore
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
     * Validators used in the constructor, and available to builders.
     */
    protected static int validateHighestThrow(int highestThrow) throws BadThrowException
    {
        if (highestThrow > MAX_THROW) throw new BadThrowException("Higest throw too large, cannot be larger than " + MAX_THROW);
        return highestThrow;
    }

    protected static int validateHighestThrow(int[] thors) throws BadThrowException
    {
        final int highestThrow = Arrays.stream(thors).max().orElse(0);
        return validateHighestThrow(highestThrow);
    }

    /**
     * Methods
     */
    public VanillaSiteswap sort(final VanillaStateSorter sorter)
    {
        try
        {
            VanillaSiteswapBuilder vanillaSiteswapBuilder = new VanillaSiteswapBuilder(this.states,
                                                                startingObjectsPerHand.length,
                                                                sorter);
            return vanillaSiteswapBuilder.buildVanillaSiteswap();
        }
        catch (InvalidSiteswapException e)
        {
            e.printStackTrace();
            return this;
        }
    }

    public VanillaSiteswap join(final VanillaSiteswap second)
    {
        throw new NotImplementedException();
    }
}
