package com.ignoretheextraclub.vanillasiteswap.sorters;

import com.ignoretheextraclub.vanillasiteswap.exceptions.PeriodException;
import com.ignoretheextraclub.vanillasiteswap.siteswap.AbstractSiteswap;
import com.ignoretheextraclub.vanillasiteswap.state.AbstractState;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
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

    private static class Rotation
    {
        private final AbstractState[] states;
        private final int[] thros;
        private final int index;
        private final boolean isStates;

        public Rotation(final AbstractState[] states, final int index)
        {
            this.states = states;
            this.index = index;
            this.thros = new int[]{};
            this.isStates = true;
        }

        public Rotation(final int[] thros, final int index)
        {
            this.thros = thros;
            this.index = index;
            this.states = new AbstractState[]{};
            this.isStates = false;
        }

        public AbstractState[] getStates()
        {
            if (!isStates) throw new UnsupportedOperationException("Did you mean int?");
            return states;
        }

        public int[] getThros()
        {
            if (isStates) throw new UnsupportedOperationException("Did you mean states?");
            return thros;
        }

        public int getIndex()
        {
            return index;
        }

        public boolean isStates()
        {
            return isStates;
        }
    }

    public static class Rotations //implements Stream
    {
        private final Rotation[] rotations;

        public Rotations(final AbstractState[] states) throws PeriodException
        {
            rotations = (Rotation[]) IntStream.range(0, AbstractSiteswap.validatePeriod(states.length))
                    .boxed()
                    .map(i -> {return new Rotation(getCopy(states, i), i);})
                    .collect(Collectors.toList()).toArray();
        }

        public Rotations(final int[] thros) throws PeriodException
        {
            rotations = (Rotation[]) IntStream.range(0, AbstractSiteswap.validatePeriod(thros.length))
                    .boxed()
                    .map(i -> {return new Rotation(getCopy(thros, i), i);})
                    .collect(Collectors.toList()).toArray();
        }

        public Rotations(final Rotation[] rotations)
        {
            this.rotations = rotations;
        }

        public List<Rotation> getMaxesByState(Function<AbstractState[], Integer> mapper)
        {
            final List<Rotation> maxes = new LinkedList<>();
            int max = Integer.MIN_VALUE;
            for (Rotation rotation : rotations)
            {
                int value = mapper.apply(rotation.getStates());
                if (value > max)
                {
                    maxes.clear();
                    maxes.add(rotation);
                    max = value;
                }
            }
            return maxes;
        }

        public List<Rotation> getMaxesByInt(Function<int[], Integer> mapper)
        {
            final List<Rotation> maxes = new LinkedList<>();
            int max = Integer.MIN_VALUE;
            for (Rotation rotation : rotations)
            {
                int value = mapper.apply(rotation.getThros());
                if (value > max)
                {
                    maxes.clear();
                    maxes.add(rotation);
                    max = value;
                }
            }
            return maxes;
        }

        public List<Rotation> getMinsByStates(Function<AbstractState[], Integer> mapper)
        {
            return getMaxesByState(abstractStates -> -1 * mapper.apply(abstractStates));
        }

        public List<Rotation> getMinsByInt(Function<int[], Integer> mapper)
        {
            return getMaxesByInt(thros -> -1 * mapper.apply(thros));
        }
    }

    private static AbstractState[] getCopy(final AbstractState[] src, final int start)
    {
        final AbstractState[] dest = new AbstractState[src.length];
        System.arraycopy(src, start, dest, 0, src.length - start);
        System.arraycopy(src, 0, dest, src.length - start, start);
        return dest;
    }

    private static int[] getCopy(final int[] src, final int start)
    {
        final int[] dest = new int[src.length];
        System.arraycopy(src, start, dest, 0, src.length - start);
        System.arraycopy(src, 0, dest, src.length - start, start);
        return dest;
    }
}
