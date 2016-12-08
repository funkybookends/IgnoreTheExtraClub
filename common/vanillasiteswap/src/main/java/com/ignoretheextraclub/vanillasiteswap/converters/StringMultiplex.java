package com.ignoretheextraclub.vanillasiteswap.converters;

import com.ignoretheextraclub.vanillasiteswap.state.MultiplexState;
import org.apache.commons.lang.NotImplementedException;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by caspar on 08/12/16.
 */
public class StringMultiplex
{
    private static final char OPEN = '[';
    private static final char CLOSE = ']';

    private static List<List<Integer>> StringToThrows(String stringSiteswap)
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

    public static String listMultiplexStatesToString(List<MultiplexState> states)
    {
        throw new NotImplementedException();
    }

    public static List<MultiplexState> stringToListMultiplexState(String stringSiteswap)
    {
        throw new NotImplementedException();
    }
}
