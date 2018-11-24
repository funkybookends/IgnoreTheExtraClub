package com.ignoretheextraclub.itec.ui;

import org.apache.commons.lang3.StringUtils;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.ignoretheextraclub.itec.causaldiagram.CausalDiagramService;
import com.ignoretheextraclub.itec.configuration.DaggerCausalDiagramComponent;

public class CausalDiagramHandler implements RequestHandler<LambdaRequest, Object>
{
	private final CausalDiagramService causalDiagramService;

	public CausalDiagramHandler()
	{
		causalDiagramService = DaggerCausalDiagramComponent.create().causalDiagramService();
	}

	public CausalDiagramHandler(final CausalDiagramService causalDiagramService)
	{
		this.causalDiagramService = causalDiagramService;
	}

	@Override
	public Object handleRequest(final LambdaRequest lambdaRequest,
	                            final Context context)
	{
		final CausalDiagramRequest causalDiagramRequest = lambdaRequest.getBody();

		final ErrorResponse.ErrorResponseBuilder errorResponse = ErrorResponse.builder().statusCode(400);

		boolean badRequest = false;

		if (StringUtils.isBlank(causalDiagramRequest.getSiteswap()))
		{
			errorResponse.errorMessage("siteswap must be provided");
			badRequest = true;
		}

		if (causalDiagramRequest.getType() == null)
		{
			errorResponse.errorMessage("type must be provided");
			badRequest = true;
		}

		if (!badRequest)
		{
			try
			{
				return causalDiagramService.getCausalDiagram(causalDiagramRequest);
			}
			catch (final Exception exception)
			{
				errorResponse.errorMessage(exception.getMessage());
			}
		}

		return errorResponse.build();
	}
}
