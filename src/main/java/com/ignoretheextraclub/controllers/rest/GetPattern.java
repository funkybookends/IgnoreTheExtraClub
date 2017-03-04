//package com.ignoretheextraclub.controllers.rest;
//
//import com.ignoretheextraclub.model.*;
//import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
//import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.FourHandedSiteswap;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//import static com.ignoretheextraclub.model.SiteswapAdapter.FOUR_HANDED_SITESWAP_SHORT;
//
///**
// * Created by caspar on 14/01/17.
// */
//@RestController
//@RequestMapping("/rest/v1/p")
//public class GetPattern
//{
//    private static final Logger LOG = LoggerFactory.getLogger(GetPattern.class);
//
//    @Autowired
//    private PatternRepository fhsPatternRepository;
//
//    @Autowired
//    private NameRepository nameRepository;
//
//    @RequestMapping(path = FOUR_HANDED_SITESWAP_SHORT + "/{name}", method = RequestMethod.GET)
//    public Pattern getFourHandedSiteswapPattern(
//            @PathVariable(value = "name") final String requestName)
//    throws InvalidSiteswapException
//    {
//        String requestId = PatternName.getId(FOUR_HANDED_SITESWAP_SHORT, requestName);
//
//        Pattern pattern;
//
//        if (nameRepository.exists(requestId))
//        {
//            PatternName patternName = nameRepository.findOne(requestId);
//            // add usage
//            patternName.setUsages(patternName.getUsages() + 1);
//            nameRepository.save(patternName);
//            pattern = fhsPatternRepository.findOne(patternName.getPatternId());
//            pattern.setName(patternName);
//        }
//        else
//        {
//            FourHandedSiteswap fourHandedSiteswap = FourHandedSiteswap.create(requestName);
//            pattern = fhsPatternRepository.save(new Pattern(fourHandedSiteswap));
//
//            pattern.setName(nameRepository.save(new PatternName(requestName, pattern, 1, requestName.equals(fourHandedSiteswap.toString()) ? 1 : 0)));
//
//            if (!requestName.equals(fourHandedSiteswap.toString()))
//            {
//                pattern.setName(nameRepository.save(new PatternName(fourHandedSiteswap.toString(), pattern, 0, 1)));
//            }
//
//        }
//        return fhsPatternRepository.save(pattern);
//    }
//}
