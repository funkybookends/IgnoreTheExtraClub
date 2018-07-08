package com.ignoretheextraclub.itec.diagram;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ignoretheextraclub.itec.exception.CausalDiagramNotAvailableException;
import com.ignoretheextraclub.itec.exception.UnknownPatternTypeException;
import com.ignoretheextraclub.itec.siteswap.SiteswapService;
import com.ignoretheextraclub.itec.siteswap.SiteswapType;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;

@Controller
public class DiagramController
{
	public static final String IMAGE_SVG_XML = "image/svg+xml";

	private final SiteswapService siteswapService;
	private final CausalDiagramService causalDiagramService;

	public DiagramController(final SiteswapService siteswapService,
	                         final CausalDiagramService causalDiagramService)
	{
		this.siteswapService = siteswapService;
		this.causalDiagramService = causalDiagramService;
	}

	/**
	 * Gets a causal diagram for the pattern
	 *
	 * @param type The type of pattern
	 * @param name The name of the pattern
	 *
	 * @return The causal diagram image svg
	 *
	 * @throws UnknownPatternTypeException        If the type is not known
	 * @throws CausalDiagramNotAvailableException If a causal diagram cannot be produced
	 * @see com.ignoretheextraclub.itec.pattern.populators.CausalDiagramLinksPatternPopulator
	 * */
	@GetMapping(value = "/p/{type}/{name}/causal-diagram", produces = IMAGE_SVG_XML)
	@ResponseBody
	public String getPatternCausalDiagram(@PathVariable("type") final String type,
	                                      @PathVariable("name") final String name)
		throws UnknownPatternTypeException, CausalDiagramNotAvailableException
	{
		final Siteswap siteswap = siteswapService.getSiteswap(SiteswapType.resolveType(type), name);
		return causalDiagramService.getCausalDiagramSvg(siteswap);
	}
}