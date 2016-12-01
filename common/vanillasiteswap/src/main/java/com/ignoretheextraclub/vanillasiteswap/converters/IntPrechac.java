package com.ignoretheextraclub.vanillasiteswap.converters;

import com.ignoretheextraclub.vanillasiteswap.exceptions.InvalidPrechacException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by caspar on 01/12/16.
 */
public class IntPrechac
{
    private static final char PASS = 'p';
    private static final char DELIMETER = ' ';
    private static final char DOT = '.';

    private static final char[] VALID_DELIMETERS = new char[]{' ', ';', ','};
    private static final Pattern PRECHAC_THROW_REGEX = Pattern.compile("^\\d+(\\.5[pP])?$");

    public static String intToPrechac(int thro)
    {
        if (thro % 2 == 0) return String.valueOf(thro/2);
        return String.valueOf(thro/2) + DOT + "5" + PASS;
    }

    public static int prechacToInt(String thro) throws InvalidPrechacException
    {
        final Matcher matcher = PRECHAC_THROW_REGEX.matcher(thro);
        final boolean matches = matcher.find();
        if (matches && thro.length() == 1) return Integer.valueOf(thro) * 2;
        else if (matches && thro.length() == 4) return Integer.valueOf(thro.substring(0, 3)) *2 ;
        else throw new InvalidPrechacException("[" + thro + "] is not a valid prechac throw");
    }

}
