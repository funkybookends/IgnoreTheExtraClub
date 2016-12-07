package com.ignoretheextraclub.vanillasiteswap.converters;

/**
 * Created by caspar on 30/11/16.
 */
public class IntVanilla
{
    public static final char INVALID_CHAR = '?';
    public static final int INVALID_INT = -1;

    /**
     * Converts a char to an int. Guaranteed to not throw an exception, returns -1 if not a valid char.
     *
     * It is your responsibility to catch invalid throws.
     * @param thro
     * @return the thro as an int, or {@link #INVALID_INT} if not valid.
     */
    public static int charToInt(char thro)
    {
        if      (thro >= '0' && thro <= '9') return thro - '0';
        else if (thro >= 'A' && thro <= 'Z') return thro - 'A' + 10;
        else if (thro >= 'a' && thro <= 'z') return thro - 'a' + 10;
        else                                 return INVALID_INT;
    }

    /**
     * Converts an int to a char. Guaranteed to not throw an exception, returns '?' if not valid.
     *
     * It is your responsibility to catch invalid throws.
     * @param thro
     * @return the thro as a char, or {@link #INVALID_CHAR} if not valid.
     */
    public static char intToChar(int thro)
    {
        if      (thro < 0 ) return INVALID_CHAR;
        else if (thro < 10) return (char) (thro + '0');
        else if (thro < 36) return (char) (thro + 'A' - 10);
        else                return INVALID_CHAR;
    }

    /**
     * Converts an array of ints to an array of chars. Guaranteed to not throw an exception
     *
     * It is your responsibility to catch invalid throws.
     * @param intThrows
     * @return charThrows, with {@link #INVALID_CHAR}s for invalid throws.
     */
    public static char[] intArrayToCharArray(int[] intThrows)
    {
        final char[] charThrows = new char[intThrows.length];
        for (int i = 0; i < intThrows.length; i++)
        {
            charThrows[i] = intToChar(intThrows[i]);
        }
        return charThrows;
    }

    /**
     * Converts an array of chars to an int array
     * @param charThrows
     * @return an array of ints, with {@link #INVALID_INT} for any invalid throws.
     */
    public static int[] charArrayToIntArray(char[] charThrows)
    {
        final int[] intThrows = new int[charThrows.length];
        for (int i = 0; i < charThrows.length; i++)
        {
            intThrows[i] = charToInt(charThrows[i]);
        }
        return intThrows;
    }

    /**
     * Converts a string to an int array. Guaranteed to not thow an exception.
     *
     * It is your responsibility to catch invalid throws.     *
     * @param stringThrows
     * @return an int array, with {@link #INVALID_INT} for any invalid throws.
     */
    public static int[] stringToIntArray(String stringThrows)
    {
        return charArrayToIntArray(stringThrows.toCharArray());
    }

    /**
     * Converts an int array to a string. Guranteed to not throw an exception
     *
     * It is your responsibility to catch invalid throws.
     * @param thros
     * @return A string representation of the throws, with {@link #INVALID_CHAR} for any invalid throws.
     */
    public static String intArrayToString(int[] thros)
    {
        return new String(intArrayToCharArray(thros));
    }
}
