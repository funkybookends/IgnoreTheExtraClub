package com.ignoretheextraclub.itec.configuration;

import javax.inject.Singleton;

import com.ignoretheextraclub.itec.causaldiagram.CausalDiagramService;

import dagger.Component;

@Singleton
@Component(modules = {CausalDiagramModuleConfiguration.class})
public interface CausalDiagramComponent
{
	CausalDiagramService causalDiagramService();
}
