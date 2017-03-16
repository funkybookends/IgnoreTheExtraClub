package com.ignoretheextraclub.model;

import java.time.Instant;
import java.util.List;

/**
 * An object should implement this interface if it wants to be displayed as a
 * whole page.
 */
public interface PageViewable
{
    String getPageTitle();
    Instant getCreatedDate();
    boolean hasAlternativeNames(); // mostly for patterns
    List<String> getAlternativeNames();
    boolean hasPageSubtitle();
    String getPageSubtitle();
    boolean hasDetails(); // mostly used for patterns
    List<Characteristic> getDetails();
    String getBody();
    // boolean commentable;
    // String getCommentableId(); // The id to use when posting for a comment
    // boolean hasComments();
    // List<Comment> getComments();
}
