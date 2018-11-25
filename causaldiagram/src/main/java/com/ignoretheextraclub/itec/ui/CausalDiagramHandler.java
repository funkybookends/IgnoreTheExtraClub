package com.ignoretheextraclub.itec.ui;

import org.apache.commons.lang3.StringUtils;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.ignoretheextraclub.itec.causaldiagram.CausalDiagramService;
import com.ignoretheextraclub.itec.configuration.DaggerCausalDiagramComponent;

import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;

@Log
public class CausalDiagramHandler implements RequestHandler<LambdaRequest, Object>
{
	private final CausalDiagramService causalDiagramService;

	public CausalDiagramHandler()
	{
		causalDiagramService = DaggerCausalDiagramComponent.create().causalDiagramService();
		log.info("Created Dagger component");
	}

	public CausalDiagramHandler(final CausalDiagramService causalDiagramService)
	{
		this.causalDiagramService = causalDiagramService;
	}

	@Override
	public Object handleRequest(final LambdaRequest lambdaRequest,
	                            final Context context)
	{
		log.info("Evaluation message");
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
				log.info("message is good, creating diagram");
				return causalDiagramService.getCausalDiagram(causalDiagramRequest);
			}
			catch (final Exception exception)
			{
				errorResponse.errorMessage(exception.getMessage());
			}
		}

		log.info("returning");
		return errorResponse.build();
	}
}
