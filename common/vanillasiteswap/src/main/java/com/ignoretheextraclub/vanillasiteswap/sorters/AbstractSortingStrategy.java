package com.ignoretheextraclub.vanillasiteswap.sorters;

import com.ignoretheextraclub.vanillasiteswap.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.vanillasiteswap.state.MultiplexState;
import com.ignoretheextraclub.vanillasiteswap.state.SynchronousState;
import com.ignoretheextraclub.vanillasiteswap.state.VanillaState;
import org.apache.commons.lang.NotImplementedException;

import java.util.List;
import java.util.stream.IntStream;

/**
 * Created by caspar on 11/12/16.
 */
public abstract class AbstractSortingStrategy
{
    /**
     * Returns the index that should be first state in the array.
     * @param unsorted
     * @return int
     */
    public int sort(VanillaState[] unsorted) throws NotImplementedException, InvalidSiteswapException
    {
        throw new NotImplementedException();
    }

    /**
     * Returns the index that should be first state in the array.
     * @param unsorted
     * @return int
     */
    public int sort(MultiplexState[] unsorted) throws NotImplementedException, InvalidSiteswapException
    {
        throw new NotImplementedException();
    }

    /**
     * Returns the index that should be first state in the array.
     * @param unsorted
     * @return int
     */
    public int sort(SynchronousState[] unsorted) throws NotImplementedException, InvalidSiteswapException
    {
        throw new NotImplementedException();
    }

    protected int getFirstMinIndex(int[] values)
    {
        return IntStream.range(0, values.length)
                .reduce((bestP, candP) -> values[bestP] < values[candP] ? bestP : candP)
                .orElse(0);
    }

    protected int getFirstMinIndex(List<Integer> values)
    {
        return IntStream.range(0, values.size())
                .reduce((bestP, candP) -> values.get(bestP) < values.get(candP) ? bestP : candP)
                .orElse(0);
    }

    protected int getFirstMaxIndex(int[] values)
    {
        return IntStream.range(0, values.length)
                .reduce((bestP, candP) -> values[bestP] > values[candP] ? bestP : candP)
                .orElse(0);
    }

    protected int getFirstMaxIndex(List<Integer> values)
    {
        return IntStream.range(0, values.size())
                .reduce((bestP, candP) -> values.get(bestP) > values.get(candP) ? bestP : candP)
                .orElse(0);
    }
}
