package com.ignoretheextraclub.itec.causaldiagram;

import com.ignoretheextraclub.itec.ui.CausalDiagramRequest;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.converter.CausalDiagramDrawer;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.properties.CausalDiagramProperties;

public interface CausalDiagramDrawerFactory
{
	CausalDiagramDrawer getCausalDiagramDrawer(CausalDiagramRequest properties, final CausalDiagramProperties causalDiagramProperties);
}
