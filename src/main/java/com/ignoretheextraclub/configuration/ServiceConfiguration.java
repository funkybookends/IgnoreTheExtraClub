package com.ignoretheextraclub.configuration;

import com.ignoretheextraclub.persistence.PatternRepository;
import com.ignoretheextraclub.service.pattern.PatternService;
import com.ignoretheextraclub.service.pattern.PatternServiceImpl;
import com.ignoretheextraclub.service.pattern.constructors.FourHandedSiteswapPatternConstructor;
import com.ignoretheextraclub.service.pattern.constructors.TwoHandedSiteswapPatternConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;

/**
 * Created by caspar on 05/03/17.
 */
@Configuration
public class ServiceConfiguration
{
    private @Autowired FourHandedSiteswapPatternConstructor fhspc;
    private @Autowired TwoHandedSiteswapPatternConstructor thspc;
    private @Autowired PatternRepository patternRepository;

    public @Bean PatternService patternService()
    {
        return new PatternServiceImpl(patternRepository, Arrays.asList(fhspc, thspc));
    }


}
