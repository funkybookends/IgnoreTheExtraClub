package com.ignoretheextraclub.service.pattern;

import com.ignoretheextraclub.exceptions.CommentException;
import com.ignoretheextraclub.exceptions.CommentNotFoundException;
import com.ignoretheextraclub.model.juggling.Pattern;
import com.ignoretheextraclub.model.request.CommentRequest;
import com.ignoretheextraclub.model.user.Comment;
import com.ignoretheextraclub.model.user.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

/**
 * Created by caspar on 15/04/17.
 */
public interface CommentService
{
    Comment addCommentAndReturnComment(Pattern pattern, User user, CommentRequest commentRequest) throws CommentException;

    Pattern addCommentAndReturnPattern(Pattern pattern, User user, CommentRequest commentRequest) throws CommentException;

    boolean setCommentVisible(String commentId, boolean visible) throws CommentNotFoundException;

    List<Comment> getUsersComments(User user) throws UsernameNotFoundException;
}
