package com.ignoretheextraclub.vanillasiteswap.state;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ignoretheextraclub.vanillasiteswap.exceptions.BadThrowException;
import com.ignoretheextraclub.vanillasiteswap.exceptions.NoTransitionException;
import com.ignoretheextraclub.vanillasiteswap.exceptions.NumObjectsException;
import com.ignoretheextraclub.vanillasiteswap.exceptions.StateSizeException;
import jdk.nashorn.internal.ir.annotations.Immutable;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by caspar on 26/11/16.
 */
@Immutable
public class VanillaState extends AbstractState
{
    @JsonIgnore
    private final int maxThrow;
    @JsonIgnore
    private final int numObjects;
    @JsonIgnore
    private final boolean[] occupied;

    /**
     * Actually constructs a new object
     * @param occupied
     * @throws StateSizeException
     * @throws NumObjectsException
     */
    private VanillaState(final boolean[] occupied) throws StateSizeException, NumObjectsException
    {
        this.maxThrow = validateSize(occupied.length);
        this.numObjects = validateNumObjects(getNumObjects(occupied));
        this.occupied = occupied;
    }

    public boolean canThrow()
    {
        return occupied[0];
    }

    @JsonIgnore
    public Set<Integer> getAvailableThrows()
    {
        final Set<Integer> availableThros = new TreeSet<>();
        if (canThrow())
        {
            for (int pos = 0; pos < maxThrow; pos++)
            {
                if (!occupied[pos]) availableThros.add(pos);
            }
            availableThros.add(maxThrow);
        }
        else
        {
            availableThros.add(0);
        }
        return availableThros;
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
    @JsonIgnore
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

    public static VanillaState getGroundState(final int maxThrow, final int numObjects)
        throws StateSizeException, NumObjectsException
    {
        final boolean[] occupied = new boolean[validateSize(maxThrow)];
        validateNumObjects(numObjects);
        for (int i = 0; i < maxThrow; i++)
        {
            occupied[i] = (i < numObjects);
        }
        return new VanillaState(occupied);
    }

    @JsonProperty("occupancy")
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
    public boolean equals(final Object o)
    {
        if (o == null) return false;
        if (this == o) return true;
        if (getClass() != o.getClass()) return false;
        VanillaState state = (VanillaState) o;
        return Arrays.equals(occupied, state.occupied);
    }

    public boolean equals(final boolean[] other)
    {
        return Arrays.equals(this.occupied, other);
    }

    @Override
    public int hashCode()
    {
        return this.toString().hashCode();
    }

    public VanillaState thro(final int thro) throws BadThrowException
    {
        if (!canThrow())
        {
            if (thro == 0)
            {
                try
                {
                    return new VanillaState(drop(occupied, false)); // throw 0
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
            if (thro == maxThrow) return new VanillaState(drop(occupied, true)); // throw max_throw
            else if (occupied[thro]) throw new BadThrowException("Cannot throw [" + thro + "], already occupied.");
            else
            {
                boolean[] nextState = copy(occupied);
                nextState[thro] = true;
                return new VanillaState(drop(nextState, false)); // throw non max throw throw
            }
        }
        catch (StateSizeException | NumObjectsException e)
        {
            throw new RuntimeException("Something went wrong! the throw should've been fine.", e);
        }
    }

    public static int transition(final VanillaState from, final VanillaState to) throws NoTransitionException
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

    @Override
    public Collection<VanillaState> getNextStates()
    {
        return getAvailableThrows().stream().map(thro -> {
            try
            {
                return thro(thro);
            }
            catch (final BadThrowException e)
            {
                throw new RuntimeException("Vanilla State could not throw an available throw", e);
            }
        }).collect(Collectors.toList());
    }

    @Override
    public boolean canTransition(AbstractState to)
    {
        try
        {
            transition(this, (VanillaState) to);
            return true;
        }
        catch (final Exception any)
        {
            return false;
        }
    }

    public static class VanillaStateBuilder
    {
        private boolean[] occupied;
        private final int maxThrow;
        private int givenObjects;
        private final int expectedObjects;

        public VanillaStateBuilder(final int maxThrow, final int expectedObjects) throws StateSizeException, NumObjectsException
        {
            this.maxThrow = validateSize(maxThrow);
            this.expectedObjects = validateNumObjects(expectedObjects);
            this.occupied = new boolean[maxThrow];
        }

        public VanillaStateBuilder thenThrow(final int thro) throws BadThrowException, NumObjectsException
        {
            if (thro < 0 || thro > maxThrow)
            {
                throw new BadThrowException("Throw [" + thro + "] out of bounds [0," + maxThrow + "]");
            }

            if (!occupied[0] && thro != 0)
            {
                givenObjects++;
            }

            if (givenObjects > expectedObjects)
            {
                throw new NumObjectsException("Given an unexpected object. Already have [" + givenObjects + "] in [" + this.toString() + "]");
            }

            if (thro == maxThrow)
            {
                occupied = drop(occupied, true);
            }
            else if (occupied[thro])
            {
                throw new BadThrowException("Throw [" + thro + "] cannot be made: [" + this.toString() + "]");
            }
            else
            {
                occupied[thro] = true;
                occupied = drop(occupied, false);
            }

            return this;
        }

        public VanillaState build() throws StateSizeException, NumObjectsException
        {
            return new VanillaState(occupied);
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

    private static String toString(final boolean[] filledPositions)
    {
        StringBuilder strBuilder = new StringBuilder();
        for (boolean filledPosition : filledPositions)
        {
            if (filledPosition) strBuilder.append(FILLED);
            else                strBuilder.append(EMPTY);
        }
        return strBuilder.toString();
    }

    private static boolean[] copy(final boolean[] positions)
    {
        boolean[] copy = new boolean[positions.length];
        System.arraycopy(positions, 0, copy, 0, positions.length);
        return copy;
    }

    private static boolean[] drop(final boolean[] filledPositions, final boolean highestState)
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

    /**
     * Some public methods to deal with VanillaState[]. Here so you don't have to go to the effort of creating a Vanilla
     * Siteswap all the time
     */

    public static int[] convert(final VanillaState[] from) throws NoTransitionException
    {
        final int[] to = new int[from.length];
        for (int i = 0; i < from.length; i++)
        {
            to[i] = transition(from[i], from[(i+1)%from.length]);
        }
        return to;
    }

    public static VanillaState[] reduce(final VanillaState[] duplicated)
    {
//        for (int i = 0; i < duplicated.length ; i++)
//        {
//            if (i % duplicated.length == 0)
//            {
//                final int repeats = duplicated.length / 2;
//                for (int j = 1; j < repeats; j++)
//                {
                    Spliterator<VanillaState> spliterator = Spliterators.spliterator(duplicated, 0, 2, Spliterator.ORDERED);
                    int j = 0;
                    while (true)
                    {

                        if (!spliterator.tryAdvance(vanillaState -> {
                            System.out.println("recieved = " + vanillaState);
                        }))
                            break;
                        j++;
                    }
//                    spliterator.forEachRemaining(System.out::println);
//                }
//            }
//        }
        return null;
    }

//    public class thing extends Spliterators
}
