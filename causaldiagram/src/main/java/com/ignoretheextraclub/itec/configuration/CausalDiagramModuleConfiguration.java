package com.ignoretheextraclub.itec.configuration;

import com.ignoretheextraclub.itec.causaldiagram.CausalDiagramService;
import com.ignoretheextraclub.itec.causaldiagram.impl.CausalDiagramServiceImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class CausalDiagramModuleConfiguration
{
	@Provides
	public CausalDiagramService causalDiagramService()
	{
		return new CausalDiagramServiceImpl();
	}
}
