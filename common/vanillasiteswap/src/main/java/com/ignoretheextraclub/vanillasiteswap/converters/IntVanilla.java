package com.ignoretheextraclub.vanillasiteswap.converters;

import java.util.Arrays;

/**
 * Created by caspar on 30/11/16.
 */
public class IntVanilla
{
    /**
     * Converts a char to an int. Guaranteed to not throw an exception, returns -1 if not a valid char. Should get
     * caught elsewhere.
     * @param thro
     * @return
     */
    public static int charToInt(char thro)
    {
        if      (thro >= '0' && thro <= '9') return thro - '0';
        else if (thro >= 'A' && thro <= 'Z') return thro - 'A' + 10;
        else if (thro >= 'a' && thro <= 'z') return thro - 'a' + 10;
        else                                 return -1;
    }

    /**
     * Converts an int to a char. Guaranteed to not throw an exception, returns '?' if not valid.
     * @param thro
     * @return
     */
    public static char intToChar(int thro)
    {
        if      (thro < 0 ) return '?';
        else if (thro < 10) return (char) (thro + '0');
        else if (thro < 36) return (char) (thro + 'A' - 10);
        else                return '?';
    }

    public static char[] intArrayToCharArray(int[] intThrows)
    {
        final char[] charThrows = new char[intThrows.length];
        for (int i = 0; i < intThrows.length; i++)
        {
            charThrows[i] = intToChar(intThrows[i]);
        }
        return charThrows;
    }

    public static int[] charArrayToIntArray(char[] charThrows)
    {
        final int[] intThrows = new int[charThrows.length];
        for (int i = 0; i < charThrows.length; i++)
        {
            intThrows[i] = charToInt(charThrows[i]);
        }
        return intThrows;
    }

    public static int[] stringToIntArray(String stringThrows)
    {
        return charArrayToIntArray(stringThrows.toCharArray());
    }
}
