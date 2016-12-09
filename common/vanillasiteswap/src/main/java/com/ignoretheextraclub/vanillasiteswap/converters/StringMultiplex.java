package com.ignoretheextraclub.vanillasiteswap.converters;

import com.sun.javaws.exceptions.InvalidArgumentException;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by caspar on 08/12/16.
 */
public class StringMultiplex
{
    private static final char OPEN = '[';
    private static final char CLOSE = ']';

    private static List<List<Integer>> stringToThrows(final String stringSiteswap)
    {
        List<List<Integer>> allThros = new LinkedList<>();

        for (int i = 0; i < stringSiteswap.toCharArray().length; )
        {
            List<Integer> beatThros = new LinkedList<>();
            int thro = IntVanilla.charToInt(stringSiteswap.charAt(i));
            if (thro < 0) //then this is not a token
            {
                i++; // move past OPEN
                while (IntVanilla.charToInt(stringSiteswap.charAt(i)) >= 0)
                {
                    beatThros.add(IntVanilla.charToInt(stringSiteswap.charAt(i)));
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

    private static String throwsToString(final List<List<Integer>> allThros) throws InvalidArgumentException
    {
        StringBuilder str = new StringBuilder();
        for (List<Integer> beatThros : allThros)
        {
            if      (beatThros.size() == 0) throw new InvalidArgumentException(new String[]{"Cannot have no throws in a beat"});
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
