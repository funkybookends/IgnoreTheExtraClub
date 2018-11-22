package com.ignoretheextraclub.itec.configuration;

import javax.inject.Singleton;

import com.ignoretheextraclub.itec.pattern.PatternService;

import dagger.Component;

@Singleton
@Component(modules = {PatternModuleConfiguration.class})
public interface PatternComponent
{
	PatternService patternService();
}
