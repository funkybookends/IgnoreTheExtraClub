package com.ignoretheextraclub.vanillasiteswap.sorters;

import com.ignoretheextraclub.vanillasiteswap.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.vanillasiteswap.exceptions.PeriodException;
import com.ignoretheextraclub.vanillasiteswap.siteswap.AbstractSiteswap;
import com.ignoretheextraclub.vanillasiteswap.state.AbstractState;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by caspar on 11/12/16.
 */
public class SortingUtils
{
    private SortingUtils(){} // To make it package private

    protected static int getFirstMinIndex(int[] values)
    {
        return IntStream.range(0, values.length)
                .reduce((bestP, candP) -> values[bestP] <= values[candP] ? bestP : candP)
                .orElse(0);
    }

    public static int getFirstMinIndex(List<Integer> values)
    {
        return IntStream.range(0, values.size())
                .reduce((bestP, candP) -> values.get(bestP) <= values.get(candP) ? bestP : candP)
                .orElse(0);
    }

    public static int getFirstMaxIndex(int[] values)
    {
        return IntStream.range(0, values.length)
                .reduce((bestP, candP) -> values[bestP] >= values[candP] ? bestP : candP)
                .orElse(0);
    }

    protected static int getFirstMaxIndex(List<Integer> values)
    {
        return IntStream.range(0, values.size())
                .reduce((bestP, candP) -> values.get(bestP) >= values.get(candP) ? bestP : candP)
                .orElse(0);
    }

    public static class Rotations<T>
    {
        private final List<Rotation<T>> rotations;

        public Rotations(final T[] states)
        {
            try
            {
                rotations = IntStream.range(0, AbstractSiteswap.validatePeriod(states.length))
                        .boxed()
                        .map(i -> new Rotation<>(states, i))
                        .collect(Collectors.toList());
            }
            catch (PeriodException e)
            {
                throw new RuntimeException("Invalid Period", e);
            }

        }

        public T[] sort(final StateSorter<T> chooser) throws InvalidSiteswapException
        {
            Rotation<T> winner = rotations.get(0);
            for (int i = 1; i < rotations.size(); i++)
            {
                winner = chooser.takeFirst(winner.getStates(), rotations.get(i).getStates()) ? winner : rotations.get(i);
            }
            return winner.getStates();
        }

        private static class Rotation<T>
        {
            private final T[] states;
            private final int index;

            public Rotation(final T[] states, final int index)
            {
                this.states = getCopy(states, index);
                this.index = index;
            }

            public T[] getStates()
            {
                return states;
            }

            public int getIndex()
            {
                return index;
            }
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> T[] getCopy(final T[] src, final int start)
    {
        final T[] dest = (T[]) Array.newInstance(src.getClass().getComponentType(), src.length);
        System.arraycopy(src, start, dest, 0, src.length - start);
        System.arraycopy(src, 0, dest, src.length - start, start);
        return dest;
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] sort(final T[] unsorted, final StateSorter sorter) throws InvalidSiteswapException
    {
        return (T[]) (new Rotations<>(unsorted)).sort(sorter);
    }

    public static <T> T[] reduce(final T[] duplicated)
    {
        final int len = duplicated.length;
        for (int i = 1; i <= len / 2; i++)
            if (len % i == 0)
                if (checkFactors(i, duplicated))
                    return Arrays.copyOf(duplicated, i);
        return duplicated;
    }

    private static <T> boolean checkFactors(final int factor, final T[] arr)
    {
        for (int j = 1; j < arr.length / factor; j++)
            if (!rangeCompare(j * factor, factor, arr))
                return false;
        return true;
    }

    private static <T> boolean rangeCompare(final int offset, final int len, final T[] arr)
    {
        for (int i = 0; i < len; i++)
            if (!arr[i].equals(arr[offset + i]))
                return false;
        return true;
    }






    public static int[] reduce(final int[] duplicated)
    {
        final int len = duplicated.length;
        for (int i = 1; i <= len / 2; i++)
            if (len % i == 0)
                if (checkFactors(i, duplicated))
                    return Arrays.copyOf(duplicated, i);
        return duplicated;
    }

    private static boolean checkFactors(final int factor, final int[] arr)
    {
        for (int j = 1; j < arr.length / factor; j++)
            if (!rangeCompare(j * factor, factor, arr))
                return false;
        return true;
    }

    private static boolean rangeCompare(final int offset, final int len, final int[] arr)
    {
        for (int i = 0; i < len; i++)
            if (!(arr[i] == arr[offset + i]))
                return false;
        return true;
    }
}
