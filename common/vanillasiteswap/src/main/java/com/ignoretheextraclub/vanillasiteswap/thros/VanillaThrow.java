package com.ignoretheextraclub.vanillasiteswap.thros;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.ignoretheextraclub.vanillasiteswap.exceptions.BadThrowException;
import com.ignoretheextraclub.vanillasiteswap.exceptions.InvalidSiteswapException;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

/**
 * Represents a Vanilla Siteswap Throw
 *
 * Contains validation to ensure it is valid. Maximum throw is 'Z' == 35. Has methods which are guranteed to not throw
 * exceptions, perhaps will return null instead. Has convenience methods for converting between representations.
 *
 * Since there are a limited number of throws, this class denies access to the constructor. Instead use the static
 * {@link #get(int)}, {@link #get(char)}, {@link #getOrNull(int)} or {@link #getOrNull(char)} to get a
 * {@link VanillaThrow} object. Internally we keep a {@link Map} of throws. You can call {@link #reset()} to empty the
 * map if required.
 */
public class VanillaThrow extends AbstractThro implements Comparable
{
    /**
     * The char returned if an int is invalid or greater than 'z'.
     */
    public static final char INVALID_CHAR = '?';

    /**
     * The int returned if a char is invalid.
     */
    public static final int INVALID_INT = -1;

    /**
     * The lowest possible throw.
     */
    public static final int MIN_THROW = 0;

    @JsonPropertyDescription("The size of the throw. Better thought of as the number of beats before this object will" +
            "be thrown again.")
    protected final int thro;

    /**
     * A store for the instances. This prevents thousands of the same instances without confining us to enums.
     */
    private static Map<Integer, VanillaThrow> instances = new TreeMap<>();

    /**
     * Constructs a throw
     * @param thro the size of the throw.
     * @throws BadThrowException if the throw is too small
     */
    protected VanillaThrow(final int thro) throws BadThrowException
    {
        if (thro < MIN_THROW) throw new BadThrowException("Cannot throw Vanilla Throw less than 0");
        this.thro = thro;
    }

    /**
     * A static method to obtain a {@link VanillaThrow} object.
     *
     * Use {@link #getOrNull(int)} if you prefer a null value returned instead of an exception for illegal throws.
     *
     * @param thro
     * @return VanillaThrow
     * @throws BadThrowException if the throw is illegal.
     */
    public static VanillaThrow get(final int thro) throws BadThrowException
    {
        if (!instances.containsKey(thro))
        {
            instances.put(thro, new VanillaThrow(thro));
        }
        return instances.get(thro);
    }

    /**
     * An alternative to {@link #get(int)} which will convert to an int for you.
     *
     * Use {@link #getOrNull(char)} if you prefer a null value to an exception if the throw is not valid.
     *
     * @param thro
     * @return
     * @throws BadThrowException
     */
    public static VanillaThrow get(final char thro) throws BadThrowException
    {
        return get(charToInt(thro));
    }

    public static VanillaThrow getOrNull(final char thro)
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

    public static VanillaThrow getOrNull(final int thro)
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

    /**
     * A method to empty the internal map of instances.
     */
    public static void reset()
    {
        instances = null;
    }

    /**
     * Get the int size of the throw.
     * @return
     */
    @JsonProperty("throw_int")
    public int getThro(){return thro;}

    @Override
    @JsonProperty("throw_string")
    public String toString()
    {
        return String.valueOf(intToChar(thro));
    }

    /**
     * Get the number of objects needed to make this throw.
     * @return int number of objects
     */
    @JsonProperty("num_objects_thrown")
    public int getNumObjectsThrown()
    {
        return thro == 0 ? 0 : 1;
    }

    @Override
    public int compareTo(Object o)
    {
        return this.getThro() - ((VanillaThrow) o).getThro();
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        //TODO maybe we can say the FHS throws are equal?

        VanillaThrow that = (VanillaThrow) o;

        return thro == that.thro;
    }

    @Override
    public int hashCode()
    {
        return thro;
    }

    /*
            Static Methods
     */

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
     * Converts an array of VanillaThros to an int array.
     *
     * @param thros
     * @return an int array
     */
    public static int[] vanillaThrowArrayToIntArray(VanillaThrow[] thros)
    {
        final int[] intThros = new int[thros.length];
        for (int i = 0; i < thros.length; i++)
        {
            intThros[i] = thros[i].getThro();
        }
        return intThros;
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
     *
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
     * Batch converts an array of ints to an array of {@link VanillaThrow}.
     *
     * Will throw a {@link BadThrowException} if any are illegal throws.
     *
     * @param siteswap
     * @return a VanillaThrow array of equivalent throws.
     * @throws InvalidSiteswapException
     */
    public static VanillaThrow[] intArrayToVanillaThrowArray(final int[] siteswap) throws BadThrowException
    {
        final VanillaThrow[] vanillaThrows = new VanillaThrow[siteswap.length];
        for (int i = 0; i < siteswap.length; i++)
        {
            vanillaThrows[i] = get(siteswap[i]);
        }
        return vanillaThrows;
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
     * Converts an array of {@link VanillaThrow} to {@link String}.
     *
     * Internally uses {@link #vanillaThrowArrayToIntArray(VanillaThrow[])} and {@link #intArrayToString(int[])}.
     *
     * @param thros
     * @return A string representation of the throws.
     */
    public static String vanillaThrowArrayToString(VanillaThrow[] thros)
    {
        return intArrayToString(vanillaThrowArrayToIntArray(thros));
    }

    /**
     * Converts an array of {@link VanillaThrow} to {@link String}.
     *
     * Internally uses {@link #stringToIntArray(String)} and {@link #intArrayToVanillaThrowArray(int[])}.
     *
     * @param siteswap
     * @return An array of {@link VanillaThrow}
     */
    public static VanillaThrow[] stringToVanillaThrowArray(String siteswap) throws BadThrowException
    {
        return intArrayToVanillaThrowArray(stringToIntArray(siteswap));
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

    /**
     * Will determine the number of objects in a pattern.
     *
     * It will not validate the pattern, provided the list is not empty it will return an int, otherwise it will throw
     * {@link java.util.NoSuchElementException}. If it is not a valid siteswap, the int may not be correct.
     *
     * You are encourage to create a {@link com.ignoretheextraclub.vanillasiteswap.siteswap.vanilla.VanillaStateSiteswap}
     * to validate the siteswap.
     *
     * @param thros
     * @return the average as an int.
     */
    public static int numObjects(final VanillaThrow[] thros)
    {
        return (int) Arrays.stream(thros).mapToInt(VanillaThrow::getThro).average().getAsDouble();
    }

    /**
     * Will find and return the highest throw in an array of throws.
     *
     * @param thros
     * @return the highest.
     */
    public static VanillaThrow getHighestThro(VanillaThrow[] thros)
    {
        VanillaThrow highest = thros[0];
        for (int i = 1; i < thros.length; i++)
            if (highest.compareTo(thros[i]) < 0)
                highest = thros[i];
        return highest;
    }
}
