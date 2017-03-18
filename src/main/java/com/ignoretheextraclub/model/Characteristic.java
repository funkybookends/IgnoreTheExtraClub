package com.ignoretheextraclub.model;

/**
 * Created by caspar on 14/03/17.
 */
public class Characteristic
{
    private final String name;
    private final String value;

    public Characteristic(final String name, final Object value)
    {
        this.name = name;
        this.value = String.valueOf(value);
    }

    public String getName()
    {
        return name;
    }

    public String getValue()
    {
        return value;
    }
}
