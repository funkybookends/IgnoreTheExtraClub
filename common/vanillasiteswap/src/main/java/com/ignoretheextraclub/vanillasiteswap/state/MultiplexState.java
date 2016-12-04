package com.ignoretheextraclub.vanillasiteswap.state;

import com.ignoretheextraclub.vanillasiteswap.converters.IntVanilla;
import com.ignoretheextraclub.vanillasiteswap.exceptions.BadThrowException;
import com.ignoretheextraclub.vanillasiteswap.exceptions.NumObjectsException;
import com.ignoretheextraclub.vanillasiteswap.exceptions.OccupancyException;
import com.ignoretheextraclub.vanillasiteswap.exceptions.StateSizeException;
import jdk.nashorn.internal.ir.annotations.Immutable;

import java.util.Arrays;

/**
 * Created by caspar on 04/12/16.
 */
@Immutable
public class MultiplexState extends AbstractState
{
    protected static final int MAX_OCCUPANCY = 4;
    protected static final int MIN_OCCUPANCY = 0;

    private final int[] occupancy;
    private final int numObjects;
    private final int maxThrow;
    private final int maxOccupancy;

    public MultiplexState(final int[] occupancy, final int maxOccupancy) throws StateSizeException, NumObjectsException, OccupancyException
    {
        maxThrow = occupancy.length;

        if (maxThrow < MIN_SIZE || maxThrow > MAX_SIZE)
        {
            throw new StateSizeException("State has [" + maxThrow + "] positions, must be between [" + MIN_SIZE + "] and [" + MAX_SIZE + "]");
        }

        this.numObjects = Arrays.stream(occupancy).sum();

        if (this.numObjects < MIN_OBJECTS || this.numObjects > MAX_OBJECTS)
        {
            throw new NumObjectsException("State has [" + this.numObjects + "] objects, must be between [" + MIN_OBJECTS + "] and [" + MAX_OBJECTS + "]");
        }

        if (Arrays.stream(occupancy).max().orElse(0) > MAX_OCCUPANCY)
        {
            throw new OccupancyException("State exceeds it's own max occupancy");
        }
        else if (Arrays.stream(occupancy).max().orElse(0) > maxOccupancy)
        {
            throw new OccupancyException("State exceeds MAX_OCCUPANCY");
        }
        else if (Arrays.stream(occupancy).min().orElse(0) < MIN_OCCUPANCY)
        {
            throw new OccupancyException("State cannot have negative occupancy");
        }

        this.maxOccupancy = maxOccupancy;
        this.occupancy = occupancy;
    }

    @Override
    public String toString()
    {
        return super.toString();
    }

    private static String toString(int[] occupancy)
    {
        return IntVanilla.intArrayToString(occupancy).replaceAll("0", EMPTY);
    }

    public boolean canThrow()
    {
        return occupancy[0] > 0;
    }

    public int numToThrow()
    {
        return occupancy[0];
    }

    @Override
    public boolean equals(final Object o)
    {
        if (o == null) return false;
        if (this == o) return true;
        if (o instanceof int[]) return Arrays.equals(this.occupancy, (int[]) o);
        if (getClass() != o.getClass()) return false;
        MultiplexState state = (MultiplexState) o;
        return Arrays.equals(occupancy, state.occupancy);
    }

    @Override
    public int hashCode()
    {
        return this.toString().hashCode();
    }

    public MultiplexState thro(final int... thros) throws BadThrowException
    {
        if (thros.length != numToThrow())
        {
            throw new BadThrowException("Needed to throw [" + numToThrow() + "] objects but only throwing [" + thros.length + "]");
        }
        else if (thros.length > MAX_OCCUPANCY)
        {
            throw new BadThrowException("Cannot throw more");
        }

        int top = 0;
        int[] nextState = copy(this.occupancy);

        for (int thro : thros)
        {
            if (thro == maxThrow) top++;
            else nextState[thro]++;
        }

        try
        {
            return new MultiplexState(drop(nextState, top), this.maxOccupancy);
        }
        catch (StateSizeException | NumObjectsException | OccupancyException e)
        {
            throw new BadThrowException("throws [" + IntVanilla.intArrayToString(thros) + "] cannot be applied to state: [" + this.toString() + "]", e);
        }
    }

    private static int[] drop(final int[] filledPositions, final int highestState)
    {
        final int maxThrow = filledPositions.length;
        int[] next = new int[maxThrow];
        System.arraycopy(filledPositions, 1, next, 0, maxThrow - 1);
        next[maxThrow-1] = highestState;
        return next;
    }

    private static int[] copy(final int[] positions)
    {
        int[] copy = new int[positions.length];
        System.arraycopy(positions, 0, copy, 0, positions.length);
        return copy;
    }
}
