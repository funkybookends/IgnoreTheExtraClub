package com.ignoretheextraclub.itec.pattern.populators;

import org.springframework.stereotype.Component;

import com.ignoretheextraclub.itec.pattern.Pattern;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;

import lombok.extern.java.Log;

@Component
@Log
public class CausalDiagramLinksPatternPopulator implements LinksPatternPopulator
{
	@Override
	public void populate(final Pattern.Links.LinksBuilder builder, final Siteswap siteswap)
	{
		builder.causalDiagramSvg("/p/" + siteswap.getType() + "/" + siteswap.toString() + "/causal-diagram");
	}
}
