package com.ignoretheextraclub.vanillasiteswap.converters;


import com.ignoretheextraclub.vanillasiteswap.exceptions.BadThrowException;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by caspar on 08/12/16.
 */
public class StringMultiplex
{
    private static final char OPEN = '[';
    private static final char CLOSE = ']';

    public static List<List<Integer>> stringToThrows(final String stringSiteswap)
    {
        List<List<Integer>> allThros = new LinkedList<>();

        for (int i = 0; i < stringSiteswap.toCharArray().length; )
        {
            List<Integer> beatThros = new LinkedList<>();
            int thro = IntVanilla.charToInt(stringSiteswap.charAt(i));
            if (thro < 0) //then this is not a token
            {
                i++; // move past OPEN
                while (IntVanilla.charToInt(stringSiteswap.charAt(i)) > 0)
                {
                    beatThros.add(IntVanilla.charToInt(stringSiteswap.charAt(i)));
                    i++;
                }
                i++; // move past CLOSE
            }
            else
            {
                beatThros.add(thro);
                i++;
            }
            allThros.add(beatThros);
        }

        return allThros;
    }

    /**
     * Converts a list of list of throws to a string.
     *
     * Silently ignores empty lists.
     *
     * @param allThros
     * @return the string representation of the throws
     */
    public static String throwsToString(final List<List<Integer>> allThros)
    {
        StringBuilder str = new StringBuilder();
        for (List<Integer> beatThros : allThros)
        {
            if      (beatThros.size() == 0) {} // Silently ignore error
            else if (beatThros.size() == 1) str.append(beatThros.get(0));
            else
            {
                str.append(OPEN);
                beatThros.forEach(str::append);
                str.append(CLOSE);
            }
        }
        return str.toString();
    }
}
