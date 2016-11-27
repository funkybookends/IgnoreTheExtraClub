package com.ignoretheextraclub.vanillasiteswap.state;

import com.ignoretheextraclub.vanillasiteswap.exceptions.*;
import jdk.nashorn.internal.ir.annotations.Immutable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

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
            return IntStream.range(0, maxThrow + 1).filter(pos ->
            {
                if (pos != maxThrow) return !occupied[pos];
                return true; //to capture max throw as available
            }).toArray();
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
        for (int position = 0; position < this.numObjects; position++) if (!occupied[position]) return false;
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

    /**
     * Overridden will match against other VanillaStates and boolean[]
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o)
    {
        if (o == null) return false;
        if (this == o) return true;
        if (o instanceof boolean[]) return Arrays.equals(this.occupied, (boolean[]) o);
        if (getClass() != o.getClass()) return false;
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
                    throw new RuntimeException("Something went wrong! you tried to throw 0 when you had to, but I erred", e);
                }
            }
            throw new BadThrowException("Cannot throw [" + thro + "], must throw 0");
        }
        try
        {
            if (thro == maxThrow) return getState(drop(occupied, true)); // throw max_throw
            else if (occupied[thro]) throw new BadThrowException("Cannot throw [" + thro + "], already occupied.");
            else
            {
                boolean[] nextState = copy(occupied);
                nextState[thro] = true;
                return getState(drop(nextState, false)); // throw non max throw throw
            }
        }
        catch (StateSizeException | NumObjectsException e)
        {
            throw new RuntimeException("Something went wrong! the throw should've been fine.", e);
        }
    }

    public static int transition(VanillaState from, VanillaState to) throws NoTransitionException
    {
        if (from.maxThrow != to.maxThrow) throw new NoTransitionException("Cannot transition between states with different max throws: [" + from.toString() + "],[" + to.toString() + "]");
        if (from.canThrow() && to.occupied[to.maxThrow - 1]) return to.maxThrow; //if can throw and highest spot occupied
        if (!from.canThrow())
        {
            if (to.equals(drop(from.occupied, false))) return 0;
            else throw new NoTransitionException("[" + from.toString() + "] needed to throw 0, but throwing a 0 does not get you to [" + to.toString() + "]");
        }
        for (int thro : from.getAvailableThrows())
        {
            try
            {
                if (from.thro(thro).equals(to)) return thro;
            }
            catch (BadThrowException e)
            {
                throw new RuntimeException("I threw a throw I thought was legal: [" + thro + "] into [" + from.toString() + "]", e);
            }
        }
        throw new NoTransitionException("Cannot transition between these two states, from [" + from.toString() + "] to [" + to.toString() + "]");
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

    private static boolean[] drop(boolean[] filledPositions, boolean highestState)
    {
        final int maxThrow = filledPositions.length;
        boolean[] next = new boolean[maxThrow];
        System.arraycopy(filledPositions, 1, next, 0, maxThrow - 1);
        next[maxThrow-1] = highestState;
        return next;
    }

    private static int getNumObjects(final boolean [] array)
    {
        int i = 0;
        for (boolean position : array) if (position) i++;
        return i;
    }
}
