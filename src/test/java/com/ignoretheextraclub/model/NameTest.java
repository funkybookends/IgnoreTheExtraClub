package com.ignoretheextraclub.model;

import com.ignoretheextraclub.configuration.MongoConfiguration;
import com.ignoretheextraclub.configuration.PersistenceConfiguration;
import com.ignoretheextraclub.persistence.Patterns;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.FourHandedSiteswap;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Matchers.notNull;

/**
 * Created by caspar on 08/02/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class NameTest
{
    @Autowired
    private Patterns patterns;

    @Test
    public void name() throws Exception
    {
        final Pattern pattern = new Pattern(FourHandedSiteswap.create("975"));
        pattern.setName(new PatternName("Holy Grail", 1, 1));

        patterns.save(pattern);

        Pattern p = patterns.get("975", FourHandedSiteswap.class);

        Assert.assertThat(pattern.getSiteswap(), equalTo(p.getSiteswap()));
        Assert.assertThat(pattern.getCreatedDate(), is(notNull()));
        Assert.assertThat(pattern.getNames(), equalTo(p.getNames()));

    }
}