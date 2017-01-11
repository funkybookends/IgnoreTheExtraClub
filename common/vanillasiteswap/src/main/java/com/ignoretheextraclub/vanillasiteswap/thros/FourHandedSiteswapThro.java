package com.ignoretheextraclub.vanillasiteswap.thros;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ignoretheextraclub.vanillasiteswap.exceptions.BadThrowException;
import com.ignoretheextraclub.vanillasiteswap.exceptions.InvalidSiteswapException;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by caspar on 07/01/17.
 */
public class FourHandedSiteswapThro extends VanillaThro
{
    public static final int[] ILLEGAL_THROWS = new int[]{1, 3};

    protected static final int MAX_THROW = charToInt('C');

    private static final String[] HEFFLISH = new String[] //TODO localise
            {
                    "gap",    //  0
                    ""   ,    //  1
                    "zip",    //  2
                    "",       //  3
                    "hold",   //  4
                    "zap",    //  5
                    "self",   //  6
                    "pass",   //  7
                    "heff",   //  8
                    "double", //  9
                    "trelf",  // 10 A
                    "triple", // 11 B
                    "quad",   // 12 C
            };

    public static final char PASS = 'p';
    public static final char DELIMETER = ' ';
    public static final char DOT = '.';
    public static final char SEPERATOR = '|';
    public static final char OPEN = '<';
    public static final char CLOSE = '>';

    public static final String PREFIX = "";
    public static final String SUFFIX = "";

    // Taken from http://jugglingedge.com/smalltalk.php?ThreadID=1655#Small11578
    public static final String THROW = "\\d+(\\.\\d*)?" + PASS + "?";
    public static final String THROWS = "((\\(" + THROW + "," + THROW + "\\))" + SEPERATOR + THROW + ")";
    public static final String THROW_SET = "((" + THROWS + " )*" + THROWS + ")";
    public static final String PRECHAC = OPEN + "(" + THROW_SET + " ?\\| ?)*" + THROW_SET + CLOSE;

    public static final Pattern P_THROW = Pattern.compile(THROW);
    public static final Pattern P_THROWS = Pattern.compile(THROWS);
    public static final Pattern P_THROW_SET = Pattern.compile(THROW_SET);
    public static final Pattern P_PRECHAC = Pattern.compile(PRECHAC);

    private static Map<Integer, FourHandedSiteswapThro> instances = new TreeMap<>();

    protected FourHandedSiteswapThro(int thro) throws BadThrowException
    {
        super(thro);
        if (thro > MAX_THROW)
        {
            throw new BadThrowException("Throw too large");
        }
        for (int illegalThrow : ILLEGAL_THROWS)
        {
            if (thro == illegalThrow)
            {
                throw new BadThrowException("Throw [" + thro + "] is illegal");
            }
        }
    }

    public static FourHandedSiteswapThro get(int thro) throws BadThrowException
    {
        if (!instances.containsKey(thro))
        {
            instances.put(thro, new FourHandedSiteswapThro(thro));
        }
        return instances.get(thro);
    }

    public static FourHandedSiteswapThro get(char thro) throws BadThrowException
    {
        return get(charToInt(thro));
    }

    @JsonProperty("hefflish")
    public String toHefflish()
    {
        return intToHefflish(thro);
    }

    /**
     * Converts a throw to hefflish. Guranteed to not throw an exception.
     * @param thro the throw to translate
     * @return the hefflish word, or an empty string if invalid.
     */
    public static String intToHefflish(int thro)
    {
        if (thro >= 0 && thro < HEFFLISH.length)
            return HEFFLISH[thro];
        return "";
    }

    /**
     * Converts a throw to a prechac representation.
     *
     * Does no checking as to the validity of the thro.
     *
     * @param thro
     * @return the prechac representation of thro
     */
    public static String fourHandedIntToPrechac(int thro)
    {
        if (thro % 2 == 0 ) return String.valueOf(thro / 2);
        return String.valueOf(thro / 2) + DOT + "5" + PASS;
    }

    public static String fourHandedIntsToPrechac(int[] thros)
    {
        return Arrays.stream(thros)
                .boxed()
                .map(FourHandedSiteswapThro::fourHandedIntToPrechac)
                .collect(Collectors.joining(String.valueOf(DELIMETER)));
    }

    public static FourHandedSiteswapThro[] intArrayToFourHandedSiteswapThrowArray(final int[] siteswap) throws
                                                                                                        InvalidSiteswapException
    {
        try
        {
            final FourHandedSiteswapThro[] thros = new FourHandedSiteswapThro[siteswap.length];
            for (int i = 0; i < siteswap.length; i++)
            {
                thros[i] = get(siteswap[i]);
            }
            return thros;
        }
        catch (final BadThrowException cause)
        {
            throw new InvalidSiteswapException("Not a valid four handed siteswap", cause);
        }
    }
}
