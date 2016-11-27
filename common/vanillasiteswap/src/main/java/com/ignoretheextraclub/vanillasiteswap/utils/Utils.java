package com.ignoretheextraclub.vanillasiteswap.utils;

/**
 * Created by caspar on 26/11/16.
 */
public class Utils {

    public static int average(int[] vanillaSiteswap) {
        int total = 0;
        for (int thro : vanillaSiteswap)
        {
            total += thro;
        }
        return total/vanillaSiteswap.length;
    }

    public static int max(int[] vanillaSiteswap)
    {
        int max = 0;
        for (int thro : vanillaSiteswap) {
            if (thro > max) max = thro;
        }
        return max;
    }
}
