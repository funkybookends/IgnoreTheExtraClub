package com.ignoretheextraclub.vanillasiteswap.sorters;

/**
 * Created by caspar on 11/12/16.
 */
public enum SortingStrategy
{
    NO_SORTING_STRATEGY(NoSortingStrategy.class),
    HIGHEST_THROW_FIRST_STRATEGY(HighestThrowFirstStrategy.class),
    FOUR_HANDED_PASSING_STRATEGY(FourHandedPassingStrategy.class);

    private final AbstractSortingStrategy strategy;

    SortingStrategy(Class<?> clazz)
    {
        try
        {
            this.strategy = (AbstractSortingStrategy) clazz.newInstance();
        }
        catch (InstantiationException | IllegalAccessException e)
        {
            throw new RuntimeException("Strategy [" + clazz.getName() + "] is not subclass of AbstractSortingStrategy");
        }
    }

    public AbstractSortingStrategy get()
    {
        return strategy;
    }
}
