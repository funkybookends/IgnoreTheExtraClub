package com.ignoretheextraclub.model.data;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by caspar on 01/04/17.
 */
public class PostTest
{
    @Test
    public void GIVEN_twoParagraphs_EXPECT_twoParagraphs() throws Exception
    {
        final Post post = new Post(null, null, "My Title", "", "A paragraph\n\nA Second paragraph",
                Collections.singletonList("tag"));
        final String expected = "<p>A paragraph</p><p>A Second paragraph</p>";

        assertThat(post.getBodyHtml()).isEqualTo(expected);
    }
}