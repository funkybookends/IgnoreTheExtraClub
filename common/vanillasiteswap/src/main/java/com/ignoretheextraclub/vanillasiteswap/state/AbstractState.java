package com.ignoretheextraclub.vanillasiteswap.state;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ignoretheextraclub.vanillasiteswap.exceptions.BadThrowException;
import com.ignoretheextraclub.vanillasiteswap.exceptions.NoTransitionException;
import com.ignoretheextraclub.vanillasiteswap.exceptions.NumObjectsException;
import com.ignoretheextraclub.vanillasiteswap.exceptions.StateSizeException;
import com.ignoretheextraclub.vanillasiteswap.thros.AbstractThro;
import jdk.nashorn.internal.ir.annotations.Immutable;

import java.util.Collection;

/**
 * Created by caspar on 04/12/16.
 */
@Immutable
public abstract class AbstractState<Throw extends AbstractThro>
{
    protected static final String EMPTY  = "_";
    protected static final String FILLED = "X";

    protected static final int MIN_SIZE =  2;
    protected static final int MAX_SIZE = 15;

    protected static final int MIN_OBJECTS =  1;
    protected static final int MAX_OBJECTS = 12;

    protected static int validateSize(final int size) throws StateSizeException
    {
        if (size < MIN_SIZE || size > MAX_SIZE) throw new StateSizeException(
                "State has [" + size + "] positions, must be between [" + MIN_SIZE + "] and [" + MAX_SIZE + "]");
        return size;
    }

    protected static int validateNumObjects(final int numObjects) throws NumObjectsException
    {
        if (numObjects < MIN_OBJECTS || numObjects > MAX_OBJECTS) throw new NumObjectsException(
                "State has [" + numObjects + "] objects, must be between [" + MIN_OBJECTS + "] and [" + MAX_OBJECTS + "]");
        return numObjects;
    }

    @JsonIgnore
    public abstract <State extends AbstractState> Collection<State> getNextStates();

    @JsonIgnore
    public abstract Collection<Throw> getAvailableThrows();

    @JsonIgnore
    public abstract boolean canTransition(AbstractState to);

    @JsonIgnore
    public abstract <State extends AbstractState<Throw>> State thro(Throw thro) throws BadThrowException;

    public abstract int getNumObjects();

    public abstract boolean isGroundState();

    @JsonIgnore
    public abstract <Thro extends AbstractThro, State extends AbstractState<Thro>> Thro getThrow(State state) throws NoTransitionException;

    public abstract boolean equals(Object o);

    public abstract int hashCode();


}
