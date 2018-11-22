package com.ignoretheextraclub.itec.ui;

import org.apache.commons.lang3.StringUtils;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.ignoretheextraclub.itec.configuration.DaggerPatternComponent;
import com.ignoretheextraclub.itec.pattern.PatternService;
import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;

public class PatternHandler implements RequestHandler<PatternRequest, PatternResponse>
{
	private static final String SITESWAP_MISSING_ERROR = "siteswap must be provided";

	private static final int OK = 200;
	private static final int BAD_REQUEST = 400;

	private final PatternService patternService;

	public PatternHandler()
	{
		patternService = DaggerPatternComponent.create().patternService();
	}

	public PatternHandler(final PatternService patternService)
	{
		this.patternService = patternService;
	}

	@Override
	public PatternResponse handleRequest(final PatternRequest patternRequest,
	                                     final Context context)
	{
		final PatternResponse.PatternResponseBuilder builder = PatternResponse.builder()
			.request(patternRequest);

		if (StringUtils.isBlank(patternRequest.getSiteswap()))
		{
			builder.errorMessage(SITESWAP_MISSING_ERROR)
				.statusCode(400);
		}
		else
		{
			getPattern(patternRequest, builder);
		}

		return builder.build();
	}

	private void getPattern(final PatternRequest patternRequest, final PatternResponse.PatternResponseBuilder builder)
	{
		try
		{
			final Pattern pattern = patternService.getPattern(patternRequest);

			builder.pattern(pattern)
				.statusCode(OK);
		}
		catch (final InvalidSiteswapException exception)
		{
			builder.errorMessage(exception.getMessage())
				.statusCode(BAD_REQUEST);
		}
	}
}
