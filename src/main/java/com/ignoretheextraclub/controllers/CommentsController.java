package com.ignoretheextraclub.controllers;

import com.codahale.metrics.Counter;
import com.codahale.metrics.MetricRegistry;
import com.ignoretheextraclub.configuration.WebSecurityConfig;
import com.ignoretheextraclub.exceptions.CommentException;
import com.ignoretheextraclub.exceptions.CommentNotFoundException;
import com.ignoretheextraclub.exceptions.UnknownPatternException;
import com.ignoretheextraclub.model.juggling.Pattern;
import com.ignoretheextraclub.model.request.CommentRequest;
import com.ignoretheextraclub.model.user.Comment;
import com.ignoretheextraclub.model.user.User;
import com.ignoretheextraclub.service.pattern.CommentService;
import com.ignoretheextraclub.service.pattern.PatternService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSender;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by caspar on 15/04/17.
 */
@Controller
public class CommentsController
{
    private static final Logger LOG = LoggerFactory.getLogger(CommentsController.class);

    public static final String COMMENT = "comment";
    public static final String COMMENTS = COMMENT + "s";
    public static final String SUBMIT = "submit";

    private final PatternService patternService;
    private final CommentService commentService;

    private Counter commentsCreated;
    private Counter commentsDeleted;
    private Counter commentsApproved;

    @Autowired
    public CommentsController(final PatternService patternService,
                              final CommentService commentService)
    {
        this.patternService = patternService;
        this.commentService = commentService;
    }

    @Autowired(required = false)
    public void configureMetrics(MetricRegistry metricRegistry)
    {
        this.commentsCreated = metricRegistry.counter(MetricRegistry.name(COMMENTS, "created"));
        this.commentsDeleted = metricRegistry.counter(MetricRegistry.name(COMMENTS, "deleted"));
        this.commentsApproved = metricRegistry.counter(MetricRegistry.name(COMMENTS, "approved"));
    }

    @RequestMapping(
            path = "/" + COMMENT + "/" + SUBMIT,
            produces = MediaType.TEXT_HTML_VALUE,
            method = RequestMethod.POST
    )
    public String submitComment(final Model model,
                                final @RequestBody CommentRequest commentRequest,
                                final @AuthenticationPrincipal User user) throws UnknownPatternException
    {
        Pattern pattern = patternService.get(commentRequest.getPatternId())
                                        .orElseThrow(() -> new UnknownPatternException(commentRequest.getPatternId())); // TODO improve error

        try
        {
            pattern = commentService.addCommentAndReturnPattern(pattern, user, commentRequest);
        }
        catch (final CommentException e)
        {
            model.addAttribute(ErrorController.ERROR, "Sorry, Could not save " + COMMENT);
        }

        model.addAttribute(PatternViewController.ITEM, pattern);
        model.addAttribute(HomePageController.SIDEBAR_NEWEST_PATTERNS, patternService.newest(0));

        commentsCreated.inc();

        return PatternViewController.DETAILSPAGE;
    }

    @RequestMapping(
            path = "/" + COMMENT + "/" + SUBMIT,
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.POST
    )
    public Comment submitComment(final @RequestBody CommentRequest commentRequest,
                                 final @AuthenticationPrincipal User user) throws UnknownPatternException, CommentException
    {
        final Pattern pattern = patternService.get(commentRequest.getPatternId())
                                              .orElseThrow(() -> new UnknownPatternException(commentRequest.getPatternId())); // TODO improve error

        final Comment comment = commentService.addCommentAndReturnComment(pattern, user, commentRequest);

        commentsCreated.inc();

        return comment;
    }

    @RequestMapping(
            path = "/" + WebSecurityConfig.ADMIN + "/" + COMMENT + "/setvisible/{visible}/{commentid}",
            method = RequestMethod.GET
    )
    public ResponseEntity setModerated(final @PathVariable(COMMENT + "id") String commentId,
                                       final @PathVariable("visible") boolean visible) throws CommentNotFoundException
    {
        return commentService.setCommentVisible(commentId, visible)
               ? ResponseEntity.ok().build()
               : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @ExceptionHandler
    public ResponseEntity<?> handleUnknownPattern(final UnknownPatternException upe)
    {
        LOG.info("", upe);
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler
    public ResponseEntity<?> handleCommentException(final CommentException commentException)
    {
        LOG.info("", commentException);
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler
    public ResponseEntity<?> handleCommentNotFoundException(final CommentNotFoundException cnfe)
    {
        LOG.info("", cnfe);
        return ResponseEntity.badRequest().build();
    }

}
