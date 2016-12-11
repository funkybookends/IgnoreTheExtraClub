package com.ignoretheextraclub.vanillasiteswap.siteswap;

import com.ignoretheextraclub.vanillasiteswap.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.vanillasiteswap.exceptions.NoTransitionException;
import com.ignoretheextraclub.vanillasiteswap.state.MultiplexState;
import jdk.nashorn.internal.ir.annotations.Immutable;
import org.apache.commons.lang.NotImplementedException;

import java.util.List;

/**
 * Created by caspar on 07/12/16.
 */
@Immutable
public class MultiplexSiteswap extends AbstractSiteswap
{
    private final List<MultiplexState> states;
    private final String stringSiteswap;

    public MultiplexSiteswap(final List<MultiplexState> states,
                             final int period,
                             final int numObjects,
                             final String stringSiteswap,
                             final int numJugglers) throws InvalidSiteswapException
    {
        super(numJugglers, period, numObjects);
        this.states = states;
        this.stringSiteswap = stringSiteswap;
    }

    @Override
    public String toString()
    {
        return stringSiteswap;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MultiplexSiteswap that = (MultiplexSiteswap) o;

        return stringSiteswap.equals(that.stringSiteswap);
    }

    @Override
    public int hashCode()
    {
        return stringSiteswap.hashCode();
    }

    protected static class MultiplexSiteswapBuilder
    {
        protected List<MultiplexState> states;
        protected int period;
        protected int numObjects;
        protected String stringSiteswap;

        private MultiplexSiteswapBuilder(final List<MultiplexState> states) throws InvalidSiteswapException
        {
            this.states = states;
            try
            {
                for (int i = 0; i < states.size() - 1; i++)
                {
                    MultiplexState.transition(states.get(i), states.get(i + 1));
                }
                MultiplexState.transition(states.get(states.size()), states.get(0));
            }
            catch (NoTransitionException e)
            {
                throw new InvalidSiteswapException("Cannot make all transitions", e);
            }

            this.period = states.size();
            this.numObjects = states.get(0).getNumObjects();
            this.stringSiteswap = "";
            throw new NotImplementedException();
        }
    }
}
