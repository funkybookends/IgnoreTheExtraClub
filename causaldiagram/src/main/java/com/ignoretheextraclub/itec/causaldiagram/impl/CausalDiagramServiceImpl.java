package com.ignoretheextraclub.itec.causaldiagram.impl;

import java.util.Collections;
import java.util.Optional;

import org.jfree.graphics2d.svg.SVGGraphics2D;

import com.ignoretheextraclub.itec.causaldiagram.CausalDiagramDrawerFactory;
import com.ignoretheextraclub.itec.causaldiagram.CausalDiagramPropertiesFactory;
import com.ignoretheextraclub.itec.causaldiagram.CausalDiagramService;
import com.ignoretheextraclub.itec.ui.CausalDiagramRequest;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.CausalDiagram;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.Hand;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.converter.CausalDiagramDrawer;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.converter.FhsToCausalDiagram;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.converter.PassingSiteswapToCausalDiagram;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.properties.CausalDiagramProperties;
import com.ignoretheextraclub.siteswapfactory.factory.impl.FourHandedSiteswapFactory;
import com.ignoretheextraclub.siteswapfactory.factory.impl.SiteswapFactoryImpl;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.FourHandedSiteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.PassingSiteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.constructors.StringToPassingSiteswapConstructor;

public class CausalDiagramServiceImpl implements CausalDiagramService
{
	private static final SiteswapFactoryImpl PASSING_SITESWAP_FACTORY = new SiteswapFactoryImpl(Collections.singletonList(StringToPassingSiteswapConstructor.get()));
	private static final Hand[] DEFAULT_FHS_ORDER = new Hand[]{Hand.RIGHT, Hand.RIGHT, Hand.LEFT, Hand.LEFT};
	private final Hand[][] DEFAULT_PASSING_ORDER = new Hand[][]
		{
			{Hand.RIGHT, Hand.LEFT},
			{Hand.RIGHT, Hand.LEFT},
			{Hand.RIGHT, Hand.LEFT},
			{Hand.RIGHT, Hand.LEFT},
			{Hand.RIGHT, Hand.LEFT},
			{Hand.RIGHT, Hand.LEFT},
			{Hand.RIGHT, Hand.LEFT},
			{Hand.RIGHT, Hand.LEFT},
		};


	private final CausalDiagramPropertiesFactory causalDiagramPropertiesFactory;
	private final CausalDiagramDrawerFactory causalDiagramDrawerFactory;
	private final CausalDiagramDrawer.GraphicsSupplier<SVGGraphics2D> graphicsSupplier;

	public CausalDiagramServiceImpl(final CausalDiagramPropertiesFactory causalDiagramPropertiesFactory,
	                                final CausalDiagramDrawerFactory causalDiagramDrawerFactory,
	                                final CausalDiagramDrawer.GraphicsSupplier<SVGGraphics2D> graphicsSupplier)
	{
		this.causalDiagramPropertiesFactory = causalDiagramPropertiesFactory;
		this.causalDiagramDrawerFactory = causalDiagramDrawerFactory;
		this.graphicsSupplier = graphicsSupplier;
	}

	@Override
	public String getCausalDiagram(final CausalDiagramRequest causalDiagramRequest)
	{
		final CausalDiagramProperties properties = causalDiagramPropertiesFactory.createProperties(causalDiagramRequest);
		final CausalDiagram causalDiagram = getCausalDiagram(causalDiagramRequest, properties);
		final CausalDiagramDrawer causalDiagramDrawer = causalDiagramDrawerFactory.getCausalDiagramDrawer(causalDiagramRequest, properties);
		final SVGGraphics2D causalDiagramSvg = causalDiagramDrawer.apply(causalDiagram, graphicsSupplier);
		return causalDiagramSvg.getSVGDocument();
	}

	private CausalDiagram getCausalDiagram(final CausalDiagramRequest causalDiagramRequest, final CausalDiagramProperties properties)
	{
		switch (causalDiagramRequest.getType())
		{
			case PASSING_SITESWAP:
				return handlePassing(causalDiagramRequest, properties);
			case FOUR_HANDED_SITESWAP:
				return handleFHS(causalDiagramRequest, properties);
			default:
				throw new IllegalStateException("Unknown siteswap type");
		}
	}

	private CausalDiagram handlePassing(final CausalDiagramRequest causalDiagramRequest, final CausalDiagramProperties properties)
	{
		final PassingSiteswap passingSiteswap = (PassingSiteswap) PASSING_SITESWAP_FACTORY.get(causalDiagramRequest.getSiteswap());

		final Hand[][] handOrder = Optional.ofNullable(causalDiagramRequest.getPassingSiteswapHandOrder())
			.orElse(DEFAULT_PASSING_ORDER);

		final PassingSiteswapToCausalDiagram diagrammer = new PassingSiteswapToCausalDiagram(properties, handOrder);

		return diagrammer.apply(passingSiteswap);
	}

	private CausalDiagram handleFHS(final CausalDiagramRequest causalDiagramRequest, final CausalDiagramProperties properties)
	{
		final FourHandedSiteswap siteswap = FourHandedSiteswapFactory.getFourHandedSiteswap(causalDiagramRequest.getSiteswap());

		final Hand[] fourHandedSiteswapHandOrder = Optional.ofNullable(causalDiagramRequest.getFourHandedSiteswapHandOrder())
			.orElse(DEFAULT_FHS_ORDER);

		final FhsToCausalDiagram diagrammer = new FhsToCausalDiagram(properties, fourHandedSiteswapHandOrder);

		return diagrammer.apply(siteswap);
	}
}
