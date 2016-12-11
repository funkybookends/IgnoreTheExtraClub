package com.ignoretheextraclub.vanillasiteswap.siteswap;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ignoretheextraclub.vanillasiteswap.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.vanillasiteswap.exceptions.NumJugglersException;
import com.ignoretheextraclub.vanillasiteswap.exceptions.NumObjectsException;
import com.ignoretheextraclub.vanillasiteswap.exceptions.PeriodException;

/**
 * Created by caspar on 10/12/16.
 */
public abstract class AbstractSiteswap
{
    public static final int MAX_PERIOD = 15;
    public static final int MIN_PERIOD = 1;

    public static final int MAX_JUGGLERS = 8;
    public static final int MIN_JUGGLERS = 1;

    public static final int MAX_OBJECTS = MAX_JUGGLERS * 7;
    public static final int MIN_OBJECTS = 1;

    @JsonProperty("num_jugglers")
    protected final int numJugglers;
    protected final int period;
    @JsonProperty("num_objects")
    protected final int numObjects;

    protected AbstractSiteswap(final int numJugglers, final int period, final int numObjects) throws InvalidSiteswapException
    {
        try {
            this.numJugglers = validateNumJugglers(numJugglers);
            this.period = validatePeriod(period);
            this.numObjects = validateNumObjects(numObjects);
        }
        catch (final PeriodException | NumJugglersException | NumObjectsException cause)
        {
            throw new InvalidSiteswapException("Cannot construct siteswap", cause);
        }
    }

    protected static int validatePeriod(final int period) throws PeriodException
    {
        if (period > MAX_PERIOD)      throw new PeriodException("Period too long, cannot be longer than " + MAX_PERIOD);
        else if (period < MIN_PERIOD) throw new PeriodException("Period too short cannot be shorter than " + MIN_PERIOD);
        return period;
    }

    protected static int validateNumObjects(final int numObjects) throws NumObjectsException
    {
        if (numObjects > MAX_OBJECTS)      throw new NumObjectsException("Too many objects, cannot have more than " + MAX_OBJECTS);
        else if (numObjects < MIN_OBJECTS) throw new NumObjectsException("Not enough objects, must at least have " + MIN_OBJECTS);
        return numObjects;
    }

    protected static int validateNumJugglers(final int numJugglers) throws NumJugglersException
    {
        if (numJugglers > MAX_JUGGLERS)      throw new NumJugglersException("Too many jugglers, cannot be more than " + MAX_JUGGLERS);
        else if (numJugglers < MIN_JUGGLERS) throw new NumJugglersException("Too few jugglers, cannot be less than " + MIN_JUGGLERS);
        return  numJugglers;
    }

    public int getNumJugglers()
    {
        return numJugglers;
    }

    public int getPeriod()
    {
        return period;
    }

    public int getNumObjects()
    {
        return numObjects;
    }
}
