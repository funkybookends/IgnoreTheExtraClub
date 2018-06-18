package com.ignoretheextraclub.itec.diagram;

import java.util.Map;
import java.util.function.Function;

import org.jfree.graphics2d.svg.SVGGraphics2D;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.ignoretheextraclub.itec.exception.CausalDiagramNotAvailableException;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.CausalDiagram;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.converter.CausalDiagramDrawer;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;

@Component
public class CausalDiagramServiceImpl implements CausalDiagramService
{
	private static final CausalDiagramDrawer.GraphicsSupplier<SVGGraphics2D> SVG_GRAPHICS_SUPPLIER = point2D -> new SVGGraphics2D((int) point2D.getX(), (int) point2D.getY());

	private final CausalDiagramDrawer causalDiagramDrawer;
	private final Map<String, Function<Siteswap, CausalDiagram>> mappers;

	public CausalDiagramServiceImpl(final CausalDiagramDrawer svgCausalDiagramDrawer,
	                                @Qualifier("siteswapTypeToCausalDiagramMapper") final Map<String, Function<Siteswap, CausalDiagram>> mappers)
	{
		this.causalDiagramDrawer = svgCausalDiagramDrawer;
		this.mappers = mappers;
	}

	@Override
	public String getCausalDiagramSvg(final Siteswap siteswap) throws CausalDiagramNotAvailableException
	{
		final Function<Siteswap, CausalDiagram> drawer = mappers.get(siteswap.getType());
		if (drawer == null)
		{
			throw new CausalDiagramNotAvailableException();
		}
		final CausalDiagram causalDiagram = drawer.apply(siteswap);
		final SVGGraphics2D apply = causalDiagramDrawer.apply(causalDiagram, SVG_GRAPHICS_SUPPLIER);
		return apply.getSVGDocument();
	}
}
