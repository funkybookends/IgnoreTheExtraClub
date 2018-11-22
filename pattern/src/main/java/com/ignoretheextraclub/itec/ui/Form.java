package com.ignoretheextraclub.itec.ui;

import com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic.Reducer;
import com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic.StreamingFilteringReducer;

public enum Form
{
	REDUCED(StreamingFilteringReducer.get()),
	ORIGINAL(Reducer.identity());

	private final Reducer reducer;

	Form(final Reducer reducer)
	{
		this.reducer = reducer;
	}

	public Reducer getReducer()
	{
		return reducer;
	}
}
