package com.ignoretheextraclub.vanillasiteswap.thros;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ignoretheextraclub.vanillasiteswap.exceptions.BadThrowException;
import com.ignoretheextraclub.vanillasiteswap.exceptions.InvalidSiteswapException;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by caspar on 24/12/16.
 */
public class VanillaThro extends AbstractThro implements Comparable
{
    public static final char INVALID_CHAR = '?';
    public static final int INVALID_INT = -1;

    public static final int MIN_THROW = 0;

    @JsonIgnore
    protected final int thro;

    private static Map<Integer, VanillaThro> instances = new TreeMap<>();

    protected VanillaThro(final int thro) throws BadThrowException
    {
        if (thro < MIN_THROW) throw new BadThrowException("Cannot throw Vanilla Throw less than 0");
        this.thro = thro;
    }

    public static VanillaThro get(final int thro) throws BadThrowException
    {
        if (!instances.containsKey(thro))
        {
            instances.put(thro, new VanillaThro(thro));
        }
        return instances.get(thro);
    }

    public static void reset()
    {
        instances = null;
    }

    public static VanillaThro get(final char thro) throws BadThrowException
    {
        return get(charToInt(thro));
    }

    @Override
    public String toString()
    {
        return String.valueOf(intToChar(thro));
    }

    @Override
    public int compareTo(Object o)
    {
        return ((VanillaThro) o).getThro() - this.getThro();
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VanillaThro that = (VanillaThro) o;

        return thro == that.thro;
    }

    @Override
    public int hashCode()
    {
        return thro;
    }

    @JsonProperty("throw_int")
    public int getThro(){return thro;}

    @JsonProperty("throw_string")
    public String getThroAsString()
    {
        return toString();
    }

    @JsonProperty("num_objects_thrown")
    public int getNumObjectsThrown()
    {
        return thro == 0 ? 0 : 1;
    }

    public static String vanillaThrowArrayToString(VanillaThro[] thros)
    {
        return intArrayToString(vanillaThrowArrayToIntArray(thros));
    }

    public static int[] vanillaThrowArrayToIntArray(VanillaThro[] thros)
    {
        final int[] intThros = new int[thros.length];
        for (int i = 0; i < thros.length; i++)
        {
            intThros[i] = thros[i].getThro();
        }
        return intThros;
    }

    /**
     * Converts a char to an int. Guaranteed to not throw an exception, returns -1 if not a valid char.
     *
     * It is your responsibility to catch invalid throws.
     * @param thro
     * @return the thro as an int, or {@link #INVALID_INT} if not valid.
     */
    public static int charToInt(final char thro)
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
    public static char intToChar(final int thro)
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
    public static char[] intArrayToCharArray(final int[] intThrows)
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
    public static int[] charArrayToIntArray(final char[] charThrows)
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
    public static int[] stringToIntArray(final String stringThrows)
    {
        return charArrayToIntArray(stringThrows.toCharArray());
    }

    /**
     * Converts an int array to a string. Guaranteed to not throw an exception
     *
     * It is your responsibility to catch invalid throws.
     * @param thros
     * @return A string representation of the throws, with {@link #INVALID_CHAR} for any invalid throws.
     */
    public static String intArrayToString(final int[] thros)
    {
        return new String(intArrayToCharArray(thros));
    }

    public static VanillaThro getOrNull(final char thro)
    {
        try
        {
            return get(thro);
        }
        catch (BadThrowException ignored)
        {
            return null;
        }
    }

    public static VanillaThro getOrNull(final int thro)
    {
        try
        {
            return get(thro);
        }
        catch (BadThrowException ignored)
        {
            return null;
        }
    }

    public static VanillaThro[] intArrayToVanillaThrowArray(final int[] siteswap) throws InvalidSiteswapException
    {
        try
        {
            final VanillaThro[] vanillaThros = new VanillaThro[siteswap.length];
            for (int i = 0; i < siteswap.length; i++)
            {
                vanillaThros[i] = get(siteswap[i]);
            }
            return vanillaThros;
        }
        catch (final BadThrowException cause)
        {
            throw new InvalidSiteswapException("Invalid Siteswap", cause);
        }
    }

    /**
     * Does not validate
     * @param thros
     * @return
     */
    public static int numObjects(final VanillaThro[] thros)
    {
        return (int) Arrays.stream(thros).mapToInt(VanillaThro::getThro).average().getAsDouble();
    }

    public static VanillaThro getHighestThro(VanillaThro[] thros)
    {
        VanillaThro highest = thros[0];
        for (int i = 1; i < thros.length; i++)
            if (highest.compareTo(thros[i]) > 0)
                highest = thros[i];
        return highest;
    }

    public static VanillaThro[] stringToVanillaThrowArray(String siteswap) throws InvalidSiteswapException
    {
        return intArrayToVanillaThrowArray(stringToIntArray(siteswap));
    }
}
