package com.ignoretheextraclub.vanillasiteswap.converters;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by caspar on 01/12/16.
 */
public class IntPrechac
{
    private static final char PASS = 'p';
    private static final char DELIMETER = ' ';
    private static final char DOT = '.';
    private static final String PREFIX = "";
    private static final String SUFFIX = "";

    private static final Pattern THROW_PATTERN = Pattern.compile("(\\d+(\\.5)?)p?");

    public static String intToPrechac(final int thro)
    {
        if (thro % 2 == 0) return String.valueOf(thro/2);
        return String.valueOf(thro/2) + DOT + "5" + PASS;
    }


    public static String intToPrechac(final int[] thros)
    {
        return intToPrechac(thros, PREFIX, SUFFIX);
    }

    public static String intToPrechac(final int[] thros, final String prefix, final String suffix)
    {
        return Arrays.stream(thros).boxed().map(IntPrechac::intToPrechac).collect(Collectors.joining(String.valueOf(DELIMETER), prefix, suffix));
    }

    /**
     * Converts a prechac to an int, converts based on the multiplier which represents half the number of hands
     *
     * It is your job to deal with converting it to the right number of hands, for example in a {@link com.ignoretheextraclub.vanillasiteswap.siteswap.FourHandedSiteswap}.
     * @param prechacThrow the precach to parse
     * @return an int representing the throw, or -1 if invalid
     */
    public static int prechacThrowToInt(final String prechacThrow)
    {
        try
        {
            Matcher matcher = THROW_PATTERN.matcher(prechacThrow);
            if (!matcher.find()) return -1;
            return Integer.valueOf(matcher.group(1));
        }
        catch (NumberFormatException nfe)
        {
            return -1;
        }
    }

    public static int[] prechacToInt(final String prechac)
    {
        final String[] splitPrechacs = prechac.split(String.valueOf(DELIMETER));
        final int[] splitInts = new int[splitPrechacs.length];
        for (int i = 0; i < splitPrechacs.length; i++)
        {
            splitInts[i] = IntPrechac.prechacThrowToInt(splitPrechacs[i]);
        }
        return splitInts;
    }

}
