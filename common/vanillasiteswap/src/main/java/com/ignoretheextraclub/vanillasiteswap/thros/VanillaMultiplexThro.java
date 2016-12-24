package com.ignoretheextraclub.vanillasiteswap.thros;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Created by caspar on 24/12/16.
 */
public class VanillaMultiplexThro extends AbstractThro
{
    private static final char OPEN = '[';
    private static final char CLOSE = ']';
    private static final String DELIM = "";

    private final int[] thros;

    public VanillaMultiplexThro(int[] thros)
    {
        this.thros = thros;
    }

    public int[] getThros()
    {
        return thros;
    }

    @Override
    public String toString()
    {
        return Arrays.stream(thros).boxed()
                .map(String::valueOf)
                .collect(Collectors.joining(null,
                                            String.valueOf(OPEN),
                                            String.valueOf(CLOSE)));
    }
}
