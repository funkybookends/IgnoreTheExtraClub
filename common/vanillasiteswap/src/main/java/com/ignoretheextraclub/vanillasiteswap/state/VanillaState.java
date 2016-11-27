package com.ignoretheextraclub.vanillasiteswap.state;

import com.ignoretheextraclub.vanillasiteswap.exceptions.*;
import jdk.nashorn.internal.ir.annotations.Immutable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by caspar on 26/11/16.
 */
@Immutable
public class VanillaState
{
    private static final String EMPTY = "_";
    private static final String FILLED = "X";

    /**
     * Temporary map as a database TODO replace with real database
     */
    private static Map<String, VanillaState> STATES = new HashMap<>();

    private static final int MIN_SIZE = 2;
    private static final int MAX_SIZE = 15;

    private static final int MIN_OBJECTS = 1;
    private static final int MAX_OBJECTS = 12;

    private final boolean[] occupied;
    private final int maxThrow;
    private final int numObjects;

    /**
     * Actually constructs a new object
     * @param occupied
     * @throws StateSizeException
     * @throws NumObjectsException
     */
    private VanillaState(final boolean[] occupied) throws StateSizeException, NumObjectsException
    {
        this.maxThrow = occupied.length;

        if (maxThrow < MIN_SIZE || maxThrow > MAX_SIZE)
        {
            throw new StateSizeException("State has [" + maxThrow + "] positions, must be between [" + MIN_SIZE + "] and [" + MAX_SIZE + "]");
        }

        this.numObjects = getNumObjects(occupied);

        if (this.numObjects < MIN_OBJECTS || this.numObjects > MAX_OBJECTS)
        {
            throw new NumObjectsException("State has [" + this.numObjects + "] objects, must be between [" + MIN_OBJECTS + "] and [" + MAX_OBJECTS + "]");
        }

        this.occupied = occupied;
    }

    private static VanillaState getState(final boolean[] filledPositions) throws StateSizeException, NumObjectsException
    {
        String stateString = toString(filledPositions);
        if (!STATES.containsKey(stateString))
        {
            STATES.put(stateString, new VanillaState(filledPositions));
        }
        return STATES.get(stateString);
    }

    private boolean canThrow()
    {
        return occupied[0];
    }

    public int[] getAvailableThrows()
    {
        if (canThrow())
        {
            int[] availableThrows = new int[this.maxThrow - this.numObjects + 1];
            int i = 0;
            for (int position = 0; position < maxThrow; position++)
            {
                if (!occupied[position])
                {
                    availableThrows[i] = position;
                    i++;
                }
            }
            availableThrows[this.maxThrow - this.numObjects] = maxThrow;
            return availableThrows;
        }
        else
        {
            return new int[]{0};
        }
    }

    public int getMaxThrow()
    {
        return maxThrow;
    }

    public int getNumObjects()
    {
        return numObjects;
    }

    public boolean isGround()
    {
        for (int position = 0; position < this.numObjects; position++)
        {
            if (!occupied[position])
            {
                return false;
            }
        }
        return true;
    }

    /**
     * Lower is better
     * @return
     */
    public int excitedness()
    {
        int result = 0;
        int position_value = 1;
        for (int position = 0; position < maxThrow; position++)
        {
            if (occupied[position])
            {
                result += position_value;
            }
            position_value *= 2;
        }
        return result;
    }

    public String toString()
    {
        return toString(this.occupied);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VanillaState state = (VanillaState) o;

        return Arrays.equals(occupied, state.occupied);

    }

    @Override
    public int hashCode()
    {
        return this.toString().hashCode();
    }

    public VanillaState thro(int thro) throws BadThrowException
    {
        if (!canThrow())
        {
            if (thro == 0)
            {
                try
                {
                    return getState(drop(occupied, false)); // throw 0
                }
                catch (StateSizeException | NumObjectsException e)
                {
                    throw new BadThrowException("Something went wrong! you tried to throw 0 when you had to, but I erred", e);
                }
            }
            throw new BadThrowException("Cannot throw [" + thro + "], must throw 0");
        }
        try
        {
            if (thro == maxThrow)
            {
                return getState(drop(occupied, true)); // throw max_throw
            }
            else if (occupied[thro])
            {
                throw new BadThrowException("Cannot throw [" + thro + "], already occupied.");
            }
            else
            {
                boolean[] nextState = copy(occupied);
                nextState[thro] = true;
                return getState(drop(nextState, false)); // throw non max throw throw
            }
        }
        catch (StateSizeException | NumObjectsException e)
        {
            throw new BadThrowException("Something went wrong! the throw should've been fine.", e);
        }
    }

    public static int transition(VanillaState from, VanillaState to) throws NoTransitionException
    {
        if (from.canThrow() && to.occupied[to.maxThrow - 1]) //if can throw and highest spot occupied
        {
            return to.maxThrow;
        }
        if (!from.canThrow())
        {
            return 0;
        }
        try
        {
            final boolean[] firstFrom1ToEnd = new boolean[from.maxThrow-1];
            final boolean[] toFrom0ToEndMinus1 = new boolean[to.maxThrow-1];

            System.arraycopy(from.occupied, 1, firstFrom1ToEnd, 0, from.maxThrow - 1);
            System.arraycopy(to.occupied, 0, toFrom0ToEndMinus1, 0, from.maxThrow - 1);

            final int differentPosition = getPositionWithDifferentValue(firstFrom1ToEnd, toFrom0ToEndMinus1);

            return differentPosition + 1;
        }
        catch (NoDifferenceException | IllegalArgumentException e)
        {
            throw new NoTransitionException("Cannot transition between these two states, from [" + from.toString() + "] to [" + to.toString() + "]", e);
        }
    }

    private static int getPositionWithDifferentValue(boolean[] first, boolean[] second) throws NoDifferenceException, IllegalArgumentException
    {
        if (first.length != second.length)
        {
            throw new IllegalArgumentException("Lengths must be the same, was given: [" + toString(first) + "], and [" + toString(second) + "]");
        }

        boolean found = false;
        int foundPosition = 0;

        for (int pos = 0; pos < first.length; pos++)
        {
            if (first[pos] != second[pos])
            {
                if (found)
                {
                    throw new IllegalArgumentException("More than one position different, was given: [" + toString(first) + "], and [" + toString(second) + "]");
                }
                found = true;
                foundPosition = pos;
            }
        }
        if (!found)
        {
            throw new NoDifferenceException("No places different, was give : [" + toString(first) + "], and [" + toString(second) + "]");
        }
        return foundPosition;
    }

    public static class VanillaStateBuilder
    {
        private boolean[] occupied;
        private final int maxThrow;
        private int givenObjects;
        private final int expectedObjects;

        public VanillaStateBuilder(int maxThrow, int expectedObjects)
        {
            this.occupied = new boolean[maxThrow];
            this.maxThrow = maxThrow;
            this.expectedObjects = expectedObjects;
        }

        public VanillaStateBuilder thenThrow(int thro) throws BadThrowException, NumObjectsException
        {
            if (thro < 0 || thro > maxThrow)    throw new BadThrowException("Throw [" + thro + "] out of bounds [0," + maxThrow + "]");
            if (!occupied[0] && thro != 0)      givenObjects++;
            if (givenObjects > expectedObjects) throw new NumObjectsException("Given an unexpected object. Already have [" + givenObjects + "] in [" + this.toString() + "]");
            if (thro == maxThrow)               occupied = drop(occupied, true);
            else if (occupied[thro])            throw new BadThrowException("Throw [" + thro + "] cannot be made: [" + this.toString() + "]");
            else                               {occupied[thro] = true; occupied = drop(occupied, false);}
            return this;
        }

        public VanillaState build() throws StateSizeException, NumObjectsException
        {
            return getState(occupied);
        }

        public int getGivenObjects()
        {
            return givenObjects;
        }

        @Override
        public String toString()
        {
            return VanillaState.toString(occupied);
        }
    }

    private static String toString(boolean[] filledPositions)
    {
        StringBuilder strBuilder = new StringBuilder();
        for (boolean filledPosition : filledPositions)
        {
            if (filledPosition)
            {
                strBuilder.append(FILLED);
            }
            else
            {
                strBuilder.append(EMPTY);
            }
        }
        return strBuilder.toString();
    }

    private static boolean[] copy(boolean[] positions)
    {
        boolean[] copy = new boolean[positions.length];
        System.arraycopy(positions, 0, copy, 0, positions.length);
        return copy;
    }

    private static boolean[] drop(boolean[] filledPositions, boolean higestState)
    {
        final int maxThrow = filledPositions.length;
        boolean[] next = new boolean[maxThrow];
        System.arraycopy(filledPositions, 1, next, 0, maxThrow - 1);
        next[maxThrow-1] = higestState;
        return next;
    }

    private static int getNumObjects(final boolean [] array)
    {
        int i = 0;
        for (boolean position : array) if (position) i++;
        return i;
    }
}
