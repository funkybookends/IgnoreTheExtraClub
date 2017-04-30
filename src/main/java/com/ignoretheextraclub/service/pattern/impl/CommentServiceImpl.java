package com.ignoretheextraclub.service.pattern.impl;

import com.ignoretheextraclub.exceptions.CommentException;
import com.ignoretheextraclub.exceptions.CommentNotFoundException;
import com.ignoretheextraclub.model.juggling.Pattern;
import com.ignoretheextraclub.model.request.CommentRequest;
import com.ignoretheextraclub.model.user.Comment;
import com.ignoretheextraclub.model.user.Permission;
import com.ignoretheextraclub.model.user.User;
import com.ignoretheextraclub.persistence.PatternRepository;
import com.ignoretheextraclub.persistence.UsersRepository;
import com.ignoretheextraclub.service.pattern.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by caspar on 29/04/17.
 */
@Service
public class CommentServiceImpl implements CommentService
{
    private static final Logger LOG = LoggerFactory.getLogger(CommentServiceImpl.class);

    private final PatternRepository patternRepository;
    private final UsersRepository usersRepository;

    @Autowired
    public CommentServiceImpl(final PatternRepository patternRepository,
                              final UsersRepository usersRepository)
    {
        this.patternRepository = patternRepository;
        this.usersRepository = usersRepository;
    }

    @Override
    public Comment addCommentAndReturnComment(Pattern pattern,
                                              final User user,
                                              final CommentRequest commentRequest) throws CommentException
    {
        pattern = addCommentAndReturnPattern(pattern, user, commentRequest);

        return pattern.getComments().get(pattern.getComments().size() - 1);
    }

    @Override
    public Pattern addCommentAndReturnPattern(final Pattern pattern,
                                              final User user,
                                              final CommentRequest commentRequest) throws CommentException
    {
        final Comment comment = convertToComment(commentRequest, user);
        pattern.addComment(comment);
        return patternRepository.save(pattern);
    }

    @Override
    public boolean setCommentVisible(final String commentId,
                                     final boolean visible) throws CommentNotFoundException
    {
        final Pattern pattern = patternRepository.findByComments_Id(commentId)
                                                 .orElseThrow(() -> new CommentNotFoundException(commentId));

        pattern.getComments()
               .stream()
               .filter(comment -> comment.getId().equals(commentId))
               .findFirst()
               .orElseThrow(() -> new CommentNotFoundException(commentId))
               .setVisible(true);

        patternRepository.save(pattern);

        return true;
    }

    @Override
    public List<Comment> getUsersComments(final User user) throws UsernameNotFoundException
    {
        final List<Pattern> patterns = patternRepository.findDistinctPatternsByComments_Username(user.getUsername());

        return patterns.stream()
                .flatMap(pattern -> pattern.getComments().stream().peek(comment -> comment.setPattern(pattern)))
                .filter(comment -> comment.getUsername().equals(user.getUsername()))
                .sorted(Comparator.comparing(Comment::getCreatedDate))
                .collect(Collectors.toList());
    }

    private Comment convertToComment(final CommentRequest commentRequest,
                                     final User user) throws CommentException
    {
        if (!user.hasPermission(Permission.COMMENTER_WRITER))
        {
            throw new CommentException("User does not have permission to comment");
        }

        final String mediaWikiBody = validateMediaWikiBody(commentRequest.getMediaWikiBody());

        return new Comment(mediaWikiBody,
                user.getUsername(),
                commentRequest.getCreatedDate(),
                user.hasPermission(Permission.COMMENTS_AUTOMATICALLY_VISIBLE));
    }

    private String validateMediaWikiBody(final String input) throws CommentException
    {
        return input; // TODO implement some sort of verification of non-shittyness
    }
}
