package com.ignoretheextraclub.itec.ui;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.ignoretheextraclub.itec.causaldiagram.CausalDiagramService;
import com.ignoretheextraclub.itec.configuration.DaggerCausalDiagramComponent;

public class CausalDiagramHandler implements RequestHandler<CausalDiagramRequest, CausalDiagramResponse>
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
	public CausalDiagramResponse handleRequest(final CausalDiagramRequest patternRequest,
	                                           final Context context)
	{
		return null;
	}
}
