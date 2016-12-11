package com.ignoretheextraclub.vanillasiteswap.sorters;

import com.ignoretheextraclub.vanillasiteswap.state.VanillaState;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by caspar on 10/12/16.
 */
public class FourHandedPassingStrategy extends AbstractSortingStrategy
{
    public int sort(final VanillaState[] unsorted)
    {
        final int period = unsorted.length;

        List<Integer> scores = IntStream.range(0, period)
                .map(start -> scoreRotation(start, period, unsorted))
                .boxed()
                .collect(Collectors.toList());

        return getFirstMinIndex(scores);
    }

    private int scoreRotation(int start, int period, VanillaState[] states)
    {
        int score = 0;
        for (int i = 0; i < period; i++)
        {
            int stateNumber = (start + i) % period;
            VanillaState state = states[stateNumber];
            int excitedness = state.excitedness();
            score += excitedness*(-i+1);
        }
        return score;
    }
}
