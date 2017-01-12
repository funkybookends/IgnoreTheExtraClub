package com.ignoretheextraclub.vanillasiteswap.converter;

import java.lang.reflect.Array;

/**
 * Created by caspar on 30/11/16.
 */
public class GlobalLocal
{
    public static int[] globalToLocal(final int[] global, final int startPos)
    {
        final int[] local = new int[global.length];
        for (int i = 0; i < global.length; i++)
        {
            local[i] = global[(startPos + (i*2)) % global.length];
        }
        return local;
    }

    public static int[] localToGlobal(final int[] local)
    {
        final int[] global = new int[local.length];
        int fromStart = 0;
        int fromMiddle = local.length/2;
        if (local.length % 2 == 1) fromMiddle++;
        int insertionIndex = 0;
        while (fromStart < (local.length / 2) + 1)
        {
            global[insertionIndex] = local[fromStart];
            insertionIndex++;
            if (fromMiddle < local.length) global[insertionIndex] = local[fromMiddle]; //incase odd
            insertionIndex++;
            fromStart++;
            fromMiddle++;
        }
        return global;
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] globalToLocal(final T[] global, final int startPos)
    {
        final T[] local = (T[]) Array.newInstance(global.getClass().getComponentType(), global.length);
        for (int i = 0; i < global.length; i++)
        {
            local[i] = global[(startPos + (i*2)) % global.length];
        }
        return local;
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] localToGlobal(final T[] local)
    {
        final T[] global = (T[]) Array.newInstance(local.getClass().getComponentType(), local.length);
        int fromStart = 0;
        int fromMiddle = local.length/2;
        if (local.length % 2 == 1) fromMiddle++;
        int insertionIndex = 0;
        while (fromStart < (local.length / 2) + 1)
        {
            global[insertionIndex] = local[fromStart];
            insertionIndex++;
            if (fromMiddle < local.length) global[insertionIndex] = local[fromMiddle]; //incase odd
            insertionIndex++;
            fromStart++;
            fromMiddle++;
        }
        return global;
    }


}
