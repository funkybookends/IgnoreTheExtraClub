package com.ignoretheextraclub.configuration;

import com.ignoretheextraclub.persistence.repository.PatternRepository;
import com.ignoretheextraclub.services.PatternService;
import com.ignoretheextraclub.services.impl.PatternServiceImpl;
import com.ignoretheextraclub.services.patternconstructors.impl.FourHandedSiteswapPatternConstructor;
import com.ignoretheextraclub.services.patternconstructors.impl.TwoHandedSiteswapPatternConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

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
