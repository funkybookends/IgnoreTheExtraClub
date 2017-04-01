package com.ignoretheextraclub.model.data;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by caspar on 01/04/17.
 */
public class PostTest
{
    @Test
    public void GIVEN_twoParagraphs_EXPECT_twoParagraphs() throws Exception
    {
        final Post post = new Post(null, null, "My Title", "", "A paragraph\n\nA Second paragraph");
        final String expected = "<p>A paragraph</p><p>A Second paragraph</p>";

        Assert.assertEquals(expected, post.getBody());
    }
}