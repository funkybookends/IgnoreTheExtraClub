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
    public static final char PASS = 'p';
    public static final char DELIMETER = ' ';
    public static final char DOT = '.';
    public static final char SEPERATOR = '|';
    public static final char OPEN = '<';
    public static final char CLOSE = '>';

    public static final String PREFIX = "";
    public static final String SUFFIX = "";

    public static final String THROW = "\\d+(\\.\\d*)?" + PASS + "?";
    public static final String THROWS = "((\\(" + THROW + "," + THROW + "\\))" + SEPERATOR + THROW + ")";
    public static final String THROW_SET = "((" + THROWS + " )*" + THROWS + ")";
    public static final String PRECHAC = OPEN + "(" + THROW_SET + " ?\\| ?)*" + THROW_SET + CLOSE;

    public static final Pattern P_THROW = Pattern.compile(THROW);
    public static final Pattern P_THROWS = Pattern.compile(THROWS);
    public static final Pattern P_THROW_SET = Pattern.compile(THROW_SET);
    public static final Pattern P_PRECHAC = Pattern.compile(PRECHAC);

    public static String fourHandedIntToPrechac(int thro)
    {
        if (thro % 2 == 0 ) return String.valueOf(thro / 2);
        return String.valueOf(thro / 2) + DOT + "5" + PASS;
    }

    public static String fourHandedIntsToPrechac(int[] thros)
    {
        return Arrays.stream(thros)
                .boxed()
                .map(IntPrechac::fourHandedIntToPrechac)
                .collect(Collectors.joining(String.valueOf(DELIMETER)));
    }

}
