package com.ignoretheextraclub.vanillasiteswap.state;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ignoretheextraclub.vanillasiteswap.exceptions.*;
import com.ignoretheextraclub.vanillasiteswap.thros.AbstractThro;
import com.ignoretheextraclub.vanillasiteswap.thros.VanillaThrow;
import jdk.nashorn.internal.ir.annotations.Immutable;

import java.util.*;

/**
 * Created by caspar on 26/11/16.
 */
@Immutable
public class VanillaState<Throw extends VanillaThrow> extends AbstractState<Throw>
{
    @JsonIgnore private final int maxThrow;
    @JsonIgnore private final int numObjects;
    @JsonIgnore private final boolean[] occupied;
    @JsonIgnore private final ThrowConstructor<Throw> throwConstructor;

    /**
     * Actually constructs a new object
     * @param occupied
     * @throws StateSizeException
     * @throws NumObjectsException
     */
    protected VanillaState(final boolean[] occupied, final ThrowConstructor<Throw> throwConstructor) throws StateSizeException, NumObjectsException
    {
        this.maxThrow = validateSize(occupied.length);
        this.numObjects = validateNumObjects(getNumObjects(occupied));
        this.occupied = occupied;
        this.throwConstructor = throwConstructor;
    }

    private Throw constructThrow(int val) throws BadThrowException
    {
        return throwConstructor.constructThrow(val);
    }

    public boolean canThrow()
    {
        return occupied[0];
    }

//    @JsonIgnore
    public Set<Throw> getAvailableThrows()
    {
        final Set<Throw> availableThros = new TreeSet<>();
        if (canThrow())
        {
            for (int pos = 0; pos < maxThrow; pos++)
            {
                if (!occupied[pos])
                {
                    try {availableThros.add(constructThrow(pos));
                    } catch (BadThrowException ignored){}
                }
            }
            try {availableThros.add(constructThrow(maxThrow));
            } catch (BadThrowException ignored){}
        }
        else
        {
            try {availableThros.add(constructThrow(0));
            } catch (BadThrowException ignored){}
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

    public boolean isGroundState()
    {
        for (int position = 0; position < this.numObjects; position++) if (!occupied[position]) return false;
        return true;
    }

    @Override
    public <Thro extends AbstractThro, State extends AbstractState<Thro>> Thro getThrow(State state) throws
                                                                                                     NoTransitionException
    {
        if (!(state instanceof VanillaState)) throw new NoTransitionException("States not of compatable types.");
        for (Throw thro : this.getAvailableThrows())
        {
            try
            {
                if (this.thro(thro).equals(state)) return (Thro) thro;
            }
            catch (BadThrowException e)
            {
                throw new RuntimeException(
                        "I threw a throw I thought was legal: [" + thro + "] into [" + this.toString() + "]", e);
            }
        }
        throw new NoTransitionException(
                "Cannot transition between these two states, from [" + this.toString() + "] to [" + state.toString() + "]");

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

//    public static VanillaState getGroundState(final int maxThrow, final int numObjects) throws StateSizeException, NumObjectsException
//    {
//        final boolean[] occupied = new boolean[validateSize(maxThrow)];
//        validateNumObjects(numObjects);
//        for (int i = 0; i < maxThrow; i++)
//        {
//            occupied[i] = (i < numObjects);
//        }
//        return new VanillaState(occupied);
//    }

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

    @Override
    public <State extends AbstractState<Throw>> State thro(Throw thro) throws BadThrowException
    {
        if (!canThrow())
        {
            if (thro.getThro() == 0)
            {
                try
                {
                    return (State) new VanillaState<>(drop(occupied, false), this.throwConstructor); // throw 0
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
            if (thro.getThro() == maxThrow) return (State) new VanillaState<>(drop(occupied, true), this.throwConstructor); // throw max_throw
            else if (occupied[thro.getThro()]) throw new BadThrowException("Cannot throw [" + thro + "], already occupied.");
            else
            {
                boolean[] nextState = copy(occupied);
                nextState[thro.getThro()] = true;
                return (State) new VanillaState<>(drop(nextState, false), this.throwConstructor); // throw non max throw throw
            }
        }
        catch (StateSizeException | NumObjectsException e)
        {
            throw new RuntimeException("Something went wrong! the throw should've been fine.", e);
        }
    }

    @Override
    public <State extends AbstractState> Collection<State> getNextStates()
    {
        try
        {
            ArrayList<State> states = new ArrayList<>();
            for (Throw thro : getAvailableThrows())
            {
                State state = (State) this.thro(thro);
                states.add(state);
            }
            return states;
        }
        catch (BadThrowException e)
        {
            throw new RuntimeException("Vanilla State could not throw an available throw", e);
        }
    }

    @Override
    public boolean canTransition(AbstractState to)
    {
        try
        {
            this.getThrow(to);
            return true;
        }
        catch (final Exception any)
        {
            return false;
        }
    }

    public static <Thro extends VanillaThrow> VanillaState<Thro> getFirstState(Thro[] thros, final ThrowConstructor<Thro> throwConstructor) throws InvalidSiteswapException
    {
        try
        {
            final int numObjects = VanillaThrow.numObjects(thros);
            final int period = thros.length;
            final VanillaThrow highestThro = VanillaThrow.getHighestThro(thros);
            final VanillaState.VanillaStateBuilder<Thro> builder = new VanillaStateBuilder<Thro>((Thro) highestThro, numObjects);

            int index = 0;
            while (builder.getGivenObjects() < numObjects || index % period != 0)
            {
                builder.thenThrow(thros[index % period]);
                index++;
            }
            return new VanillaState<Thro>(builder.getOccupied(), throwConstructor);
        }
        catch (final StateSizeException | NumObjectsException | BadThrowException cause)
        {
            throw new InvalidSiteswapException("Could not determine first state, invalid siteswap", cause);
        }
    }

    protected static class VanillaStateBuilder<Thro extends VanillaThrow>
    {
        private boolean[] occupied;
        private final int maxThrow;
        private int givenObjects;
        private final int expectedObjects;

        public VanillaStateBuilder(final Thro maxThrow, final int expectedObjects) throws StateSizeException, NumObjectsException
        {
            this.maxThrow = validateSize(maxThrow.getThro());
            this.expectedObjects = validateNumObjects(expectedObjects);
            this.occupied = new boolean[maxThrow.getThro()];
        }

        public VanillaStateBuilder thenThrow(final Thro vThro) throws BadThrowException, NumObjectsException
        {
            final int thro = vThro.getThro();
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

        public boolean[] getOccupied() throws StateSizeException, NumObjectsException
        {
            return occupied;
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

    public interface ThrowConstructor<Throw extends VanillaThrow>
    {
        Throw constructThrow(int val) throws BadThrowException;
    }
}
