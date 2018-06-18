package com.ignoretheextraclub.itec.diagram;

import com.ignoretheextraclub.itec.exception.CausalDiagramNotAvailableException;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;

public interface CausalDiagramService
{
	/**
	 * Gets a causal diagram for a siteswap.
	 *
	 * @param siteswap The siteswap to get a causal diagram for
	 *
	 * @return The causal diagram
	 */
	String getCausalDiagramSvg(Siteswap siteswap) throws CausalDiagramNotAvailableException;
}
