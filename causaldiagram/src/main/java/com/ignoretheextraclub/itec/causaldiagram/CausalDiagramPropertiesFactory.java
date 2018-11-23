package com.ignoretheextraclub.itec.causaldiagram;

import com.ignoretheextraclub.itec.ui.CausalDiagramRequest;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.properties.CausalDiagramProperties;

public interface CausalDiagramPropertiesFactory
{
	CausalDiagramProperties createProperties(CausalDiagramRequest causalDiagramRequest);
}
