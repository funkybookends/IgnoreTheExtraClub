package com.ignoretheextraclub.vanillasiteswap.state;

import jdk.nashorn.internal.ir.annotations.Immutable;

/**
 * Created by caspar on 04/12/16.
 */
@Immutable
public abstract class AbstractState
{
    protected static final String EMPTY  = "_";
    protected static final String FILLED = "X";

    protected static final int MIN_SIZE =  2;
    protected static final int MAX_SIZE = 15;

    protected static final int MIN_OBJECTS =  1;
    protected static final int MAX_OBJECTS = 12;

}
