package com.ignoretheextraclub.itec.configuration;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.jfree.graphics2d.svg.SVGGraphics2D;

import com.ignoretheextraclub.itec.causaldiagram.CausalDiagramDrawerFactory;
import com.ignoretheextraclub.itec.causaldiagram.CausalDiagramPropertiesFactory;
import com.ignoretheextraclub.itec.causaldiagram.CausalDiagramService;
import com.ignoretheextraclub.itec.causaldiagram.impl.CausalDiagramServiceImpl;
import com.ignoretheextraclub.itec.causaldiagram.impl.DefaultPropertiesCausalDiagramFactory;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.converter.CausalDiagramDrawer;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.converter.CausalDiagramToSvg;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.factory.ArrowFactory;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.factory.RotationMarkerFactory;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.factory.SwapFactory;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.factory.impl.DefaultArrowFactory;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.factory.impl.DefaultRotationMarkerFactory;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.factory.impl.DefaultSwapFactory;

import dagger.Module;
import dagger.Provides;

@Module
public class CausalDiagramModuleConfiguration
{
	@Provides
	@Singleton
	@Inject
	public CausalDiagramService causalDiagramService(final CausalDiagramPropertiesFactory causalDiagramPropertiesFactory,
	                                                 final CausalDiagramDrawerFactory causalDiagramDrawerFactory,
	                                                 final CausalDiagramDrawer.GraphicsSupplier<SVGGraphics2D> graphicsSupplier)
	{
		return new CausalDiagramServiceImpl(causalDiagramPropertiesFactory, causalDiagramDrawerFactory, graphicsSupplier);
	}

	@Provides
	@Singleton
	public CausalDiagramPropertiesFactory causalDiagramPropertiesFactory()
	{
		return new DefaultPropertiesCausalDiagramFactory();
	}

	@Provides
	@Singleton
	public CausalDiagramDrawerFactory causalDiagramDrawer()
	{
		return (request, causalDiagramProperties) -> {
			final SwapFactory swapFactory = new DefaultSwapFactory(causalDiagramProperties);
			final ArrowFactory arrowFactory = new DefaultArrowFactory(causalDiagramProperties);
			final RotationMarkerFactory rotationMarkerFactory = new DefaultRotationMarkerFactory(causalDiagramProperties);
			return new CausalDiagramToSvg(causalDiagramProperties, swapFactory, arrowFactory, rotationMarkerFactory);
		};
	}

	@Provides
	@Singleton
	public CausalDiagramDrawer.GraphicsSupplier<SVGGraphics2D> graphicsSupplier()
	{
		return (point) -> new SVGGraphics2D((int) point.getX(), (int) point.getY());
	}
}
