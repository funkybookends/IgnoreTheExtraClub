package com.ignoretheextraclub.itec.ui;

import com.ignoretheextraclub.siteswapfactory.sorters.StartingStrategy;
import com.ignoretheextraclub.siteswapfactory.sorters.impl.FourHandedPassingStrategy;
import com.ignoretheextraclub.siteswapfactory.sorters.impl.HighestThrowFirstStrategy;
import com.ignoretheextraclub.siteswapfactory.sorters.impl.NoStartingStrategy;

public enum SortType
{
	NO_SORT(NoStartingStrategy.get()),
	FOUR_HANDED_PASSING(FourHandedPassingStrategy.get()),
	HIGHEST_THROW_FIRST(HighestThrowFirstStrategy.get());

	private final StartingStrategy startingStrategy;

	SortType(final StartingStrategy startingStrategy)
	{
		this.startingStrategy = startingStrategy;
	}

	public StartingStrategy getStartingStrategy()
	{
		return startingStrategy;
	}
}
