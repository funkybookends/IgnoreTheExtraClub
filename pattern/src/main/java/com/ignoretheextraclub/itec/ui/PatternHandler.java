package com.ignoretheextraclub.itec.ui;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.ignoretheextraclub.itec.configuration.Configuration;
import com.ignoretheextraclub.itec.pattern.PatternService;
import com.ignoretheextraclub.itec.siteswap.impl.SiteswapType;

public class PatternHandler implements RequestHandler<PatternRequest, Pattern>
{
	private final PatternService patternService;

	public PatternHandler()
	{
		patternService = Configuration.PATTERN_SERVICE.get();
	}

	public PatternHandler(final PatternService patternService)
	{
		this.patternService = patternService;
	}

	@Override
	public Pattern handleRequest(final PatternRequest input, final Context context)
	{
		if (input.getType().isEmpty())
		{
			return patternService.getPattern(input.getSiteswap());
		}
		else
		{
			return patternService.getPattern(SiteswapType.resolveType(input.getType()), input.getSiteswap());
		}
	}
}
