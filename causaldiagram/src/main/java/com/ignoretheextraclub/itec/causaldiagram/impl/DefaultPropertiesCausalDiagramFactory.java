package com.ignoretheextraclub.itec.causaldiagram.impl;

import com.ignoretheextraclub.itec.causaldiagram.CausalDiagramPropertiesFactory;
import com.ignoretheextraclub.itec.ui.CausalDiagramRequest;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.properties.CausalDiagramProperties;

public class DefaultPropertiesCausalDiagramFactory implements CausalDiagramPropertiesFactory
{
	@Override
	public CausalDiagramProperties createProperties(final CausalDiagramRequest causalDiagramRequest)
	{
		return new CausalDiagramProperties();
	}
}
