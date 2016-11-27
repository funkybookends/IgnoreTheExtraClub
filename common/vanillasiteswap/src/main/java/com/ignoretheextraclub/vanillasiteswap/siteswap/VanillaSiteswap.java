package com.ignoretheextraclub.vanillasiteswap.siteswap;

import com.ignoretheextraclub.vanillasiteswap.exceptions.*;
import com.ignoretheextraclub.vanillasiteswap.state.VanillaState;
import com.ignoretheextraclub.vanillasiteswap.utils.Utils;
import jdk.nashorn.internal.ir.annotations.Immutable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.ignoretheextraclub.vanillasiteswap.state.VanillaState.*;

/**
 * Created by caspar on 26/11/16.
 */
@Immutable
public class VanillaSiteswap
{
    private static final int MAX_PERIOD = 15;

    /**
     * The list of states, ordered, and possibly sorted
     */
    private final List<VanillaState> states = new LinkedList<>();

    /**
     * A pattern is prime if it does not revisit a state twice.
     *
     * If it does this implies it can be decomposed into two or more siteswaps TODO determine decompositions
     */
    private final boolean prime;

    private final int period;
    private final int numObjects;

    /**
     * A pattern is grounded if it goes through the ground state, the ground state is the lowest level of excitiation
     */
    private final boolean grounded;

    private final int highestThrow;
    private final boolean sorted;
    private final String stringSiteswap;
    private final int[] intSiteswap;

    private VanillaSiteswap(int[] vanillaSiteswap, boolean sort) throws InvalidSiteswapException
    {
        if (vanillaSiteswap.length > MAX_PERIOD || vanillaSiteswap.length < 1)
        {
            throw new InvalidSiteswapException("Invalid siteswap length");
        }
        this.highestThrow = Utils.max(vanillaSiteswap);
        this.numObjects = Utils.average(vanillaSiteswap);
        this.period = vanillaSiteswap.length;
        try
        {
            buildStates(vanillaSiteswap);
            this.grounded = containsAGroundState(states);
            this.prime = !containsARepeatedState(); // both done here because they can be used during sorting
            if (sort)
            {
                sort();
            }
            this.sorted = sort;
            this.intSiteswap = getAllThrows();
            this.stringSiteswap = toString(this.intSiteswap);
        }
        catch (BadThrowException | NumObjectsException | StateSizeException | NoTransitionException e)
        {
            throw new InvalidSiteswapException("Invalid Siteswap [" + toString(vanillaSiteswap) + "]", e);
        }

        if (states.size() != period)
        {
            throw new InvalidSiteswapException("This shouldn't happen. Built states.size() != vanillaSiteswap.length");
        }
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

    public static VanillaSiteswap parse(int[] siteswap, boolean sort) throws InvalidSiteswapException
    {
        return new VanillaSiteswap(siteswap, sort);
    }

    public static VanillaSiteswap parse(String vanillaSiteswap, boolean sort) throws InvalidSiteswapException
    {
        try
        {
            return new VanillaSiteswap(parseGlobalString(vanillaSiteswap), sort);
        }
        catch (BadThrowException e)
        {
            throw new InvalidSiteswapException("Siteswap [" + vanillaSiteswap + "] is invalid", e);
        }
    }

    private boolean containsARepeatedState()
    {
        Set<VanillaState> stateSet = states.stream().collect(Collectors.toSet());
        return stateSet.size() != states.size();
    }

    private void buildStates(int[] vanillaSiteswap) throws NumObjectsException, BadThrowException, StateSizeException, InvalidSiteswapException
    {
        final VanillaStateBuilder builder = new VanillaStateBuilder(this.highestThrow, numObjects);
        int index = 0;
        while (builder.getGivenObjects() < numObjects || index % period != 0)
        {
            builder.thenThrow(vanillaSiteswap[index % period]);
            index++;
        }

        VanillaState state = builder.build();
        states.add(state);

        for (int pos = 0; pos < vanillaSiteswap.length - 1; pos++)
        {
            state = state.thro(vanillaSiteswap[pos]);
            states.add(state);
        }
        if (!state.thro(vanillaSiteswap[period - 1]).equals(states.get(0)))
        {
            throw new InvalidSiteswapException("Did not return to original state. Final state: [" + states.get(period-1) + "], original state: [" + states.get(0) + "]");
        }
    }


    /**
     * Sorts the siteswap, finds the groundiest first throw and makes that the first throw.
     */
    private void sort()
    {
        List<Integer> scores = IntStream.range(0, period).map((start) -> scoreRotation(start)).boxed().collect(Collectors.toList());
        int minPosition = IntStream.range(0, period).reduce((bestP, candP) -> scores.get(bestP) < scores.get(candP) ? bestP : candP).getAsInt();
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

    private static boolean containsAGroundState(List<VanillaState> states)
    {
        for (VanillaState state : states)
        {
            if (state.isGround())
            {
                return true;
            }
        }
        return false;
    }
    
    protected static int[] parseGlobalString(String stringSiteswap) throws BadThrowException
    {
        final int[] intSiteswap = new int[stringSiteswap.length()];

        for (int position = 0; position < stringSiteswap.length(); position++)
        {
            intSiteswap[position] = charToInt(stringSiteswap.charAt(position));
        }

        return intSiteswap;
    }

    private static int charToInt(char thro) throws BadThrowException
    {
        if      (thro >= '0' && thro <= '9') return thro - '0';
        else if (thro >= 'A' && thro <= 'Z') return thro - 'A' + 10;
        else if (thro >= 'a' && thro <= 'z') return thro - 'a' + 10;
        else                                 throw new BadThrowException("[" + thro + "] is not a valid throw");
    }

    private static char intToChar(int thro) throws BadThrowException
    {
        if      (thro < 0 ) throw new BadThrowException("Cannot throw less than 0, was given [" + thro + "]");
        else if (thro < 10) return (char) (thro + '0');
        else if (thro < 36) return (char) (thro + 'A' - 10);
        else                throw new BadThrowException("Cannot throw larger than 36/Z, was given [" + thro + "]");
    }

    public boolean isPrime()
    {
        return prime;
    }

    public int getPeriod()
    {
        return period;
    }

    public int getNumObjects()
    {
        return numObjects;
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

    /**
     * Converts an int[] to a string, guanrateed to never throw an exception - replaces any unknowns with "-"
     * @param intSiteswap
     * @return String representation of intSiteswap
     */
    private static String toString(int[] intSiteswap)
    {
        StringBuilder stringBuilder = new StringBuilder();
        for (int intThrow : intSiteswap)
        {
            try
            {
                stringBuilder.append(intToChar(intThrow));
            } catch (BadThrowException e)
            {
                stringBuilder.append("-");
            }
        }
        return stringBuilder.toString();
    }

    @Override
    public String toString()
    {
        return this.stringSiteswap;
    }
}
