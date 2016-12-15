package com.ignoretheextraclub.vanillasiteswap.sorters;

import com.ignoretheextraclub.vanillasiteswap.state.AbstractState;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
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

    public class Rotations // implements Stream
    {
        private final List<Predicate<? super AbstractState[]>> predicates = new LinkedList<>();
        private final AbstractState[] states;

        public Rotations(final AbstractState[] states)
        {
            this.states = new AbstractState[states.length];
            System.arraycopy(states, 0, this.states, 0, states.length);
        }

        public List<AbstractState[]> max(Comparator<? super AbstractState[]> comparator)
        {
            List<AbstractState[]> maxes = new LinkedList<>();
            maxes.add(states);
            IntStream.range(1, states.length).forEach(i ->
                  {
                      AbstractState[] rot = getCopy(states, i);
                      int diff = comparator.compare(maxes.get(0), rot);
                      if (diff == 0)
                      {
                          maxes.add(rot);
                      }
                      else if (diff > 0)
                      {
                          maxes.clear();
                          maxes.add(rot);
                      }
                      else
                      {
                          //rot is not new max
                      }
                  });
            return maxes;
        }

        public List<AbstractState[]> min(Comparator<? super AbstractState[]> comparator)
        {
            return max((a, b) -> (-1 * comparator.compare(a, b)));
        }

        private AbstractState[] getCopy(final AbstractState[] src, final int start)
        {
            final AbstractState[] dest = new AbstractState[src.length];
            System.arraycopy(src, start, dest, 0, src.length - start);
            System.arraycopy(src, 0, dest, src.length - start, start);
            return dest;
        }
    }
}
