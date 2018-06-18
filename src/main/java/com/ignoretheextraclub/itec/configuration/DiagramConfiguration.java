package com.ignoretheextraclub.itec.configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ignoretheextraclub.siteswapfactory.diagram.causal.CausalDiagram;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.converter.CausalDiagramDrawer;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.converter.CausalDiagramToSvg;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.converter.FhsToCausalDiagram;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.converter.PassingSiteswapToCausalDiagram;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.factory.impl.DefaultArrowFactory;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.factory.impl.DefaultRotationMarkerFactory;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.factory.impl.DefaultSwapFactory;
import com.ignoretheextraclub.siteswapfactory.diagram.causal.properties.CausalDiagramProperties;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.FourHandedSiteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.PassingSiteswap;

@Configuration
public class DiagramConfiguration
{
	@Bean
	public CausalDiagramDrawer svgCausalDiagramDrawer(final CausalDiagramProperties causalDiagramProperties)
	{
		return new CausalDiagramToSvg(causalDiagramProperties,
			new DefaultSwapFactory(causalDiagramProperties),
			new DefaultArrowFactory(causalDiagramProperties),
			new DefaultRotationMarkerFactory(causalDiagramProperties));
	}

	@Bean
	public Map<String, Function<Siteswap, CausalDiagram>> siteswapTypeToCausalDiagramMapper(final CausalDiagramProperties causalDiagramProperties)
	{
		final FhsToCausalDiagram fhsToCausalDiagram = new FhsToCausalDiagram(causalDiagramProperties);
		final PassingSiteswapToCausalDiagram passingSiteswapToCausalDiagram = new PassingSiteswapToCausalDiagram(causalDiagramProperties);

		final Map<String, Function<Siteswap, CausalDiagram>> map = new HashMap<>();

		map.put("Four Handed Siteswap", (siteswap -> fhsToCausalDiagram.apply((FourHandedSiteswap) siteswap)));
		map.put("Synchronous Passing Siteswap", (siteswap -> passingSiteswapToCausalDiagram.apply((PassingSiteswap) siteswap)));

		return map;
	}

	@Bean
	public CausalDiagramProperties causalDiagramProperties()
	{
		return new CausalDiagramProperties();
	}
}
