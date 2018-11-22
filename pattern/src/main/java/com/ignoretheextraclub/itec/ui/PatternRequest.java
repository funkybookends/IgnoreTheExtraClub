package com.ignoretheextraclub.itec.ui;

import com.ignoretheextraclub.itec.siteswap.impl.SiteswapType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatternRequest
{
	String siteswap;
	SiteswapType type;
	SortType sort;
    Form form;
}
