package com.ignoretheextraclub.vanillasiteswap.converters;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Created by caspar on 01/12/16.
 */
public class IntPrechac
{
    private static final char PASS = 'p';
    private static final char DELIMETER = ' ';
    private static final char DOT = '.';

    public static String intToPrechac(int thro)
    {
        if (thro % 2 == 0) return String.valueOf(thro/2);
        return String.valueOf(thro/2) + DOT + "5" + PASS;
    }

    public static String intToPrechac(int[] thros)
    {
        return Arrays.stream(thros).boxed().map(IntPrechac::intToPrechac).collect(Collectors.joining(String.valueOf(DELIMETER), "", ""));
    }

}
