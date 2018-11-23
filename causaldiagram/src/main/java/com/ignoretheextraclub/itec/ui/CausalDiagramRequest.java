package com.ignoretheextraclub.itec.ui;

import java.util.Map;

import com.ignoretheextraclub.siteswapfactory.diagram.causal.Hand;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CausalDiagramRequest
{
	String siteswap;
	SiteswapType type;
	Hand[] fourHandedSiteswapHandOrder;
	Hand[][] passingSiteswapHandOrder;
	@Singular Map<String, Object> causalDiagramProperties;
}
