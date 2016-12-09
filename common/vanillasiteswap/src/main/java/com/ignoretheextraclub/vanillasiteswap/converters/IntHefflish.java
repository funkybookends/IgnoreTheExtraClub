package com.ignoretheextraclub.vanillasiteswap.converters;

/**
 * Created by caspar on 09/12/16.
 */
public class IntHefflish
{
    private static final String[] HEFFLISH = new String[]
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
                    "trelf",  // 10
                    "triple", // 11
                    "quad",   // 12
            };

    /**
     * Converts a throw to hefflish. Guranteed to not throw an exception.
     * @param thro
     * @return the hefflish word, or an empty string if invalid.
     */
    public static String intToHefflish(int thro)
    {
        if (thro > 0 && thro < HEFFLISH.length - 1)
            return HEFFLISH[thro];
        return "";
    }
}
