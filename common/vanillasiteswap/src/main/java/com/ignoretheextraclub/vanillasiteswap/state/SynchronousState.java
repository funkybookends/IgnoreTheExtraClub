package com.ignoretheextraclub.vanillasiteswap.state;

import com.ignoretheextraclub.vanillasiteswap.thros.SynchronousThrow;

import java.util.Collection;

/**
 * Created by caspar on 07/12/16.
 */
public class SynchronousState extends AbstractState
{
    @Override
    public Collection<SynchronousState> getNextStates()
    {
        return null;
    }

    @Override
    public boolean canTransition(AbstractState to)
    {
        return false;
    }
}
