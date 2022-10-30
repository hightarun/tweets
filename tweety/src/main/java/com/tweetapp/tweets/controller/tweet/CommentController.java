package com.tweetapp.tweets.controller.tweet;

import com.tweetapp.tweets.config.JwtTokenUtil;
import com.tweetapp.tweets.controller.authentication.AuthenticationController;
import com.tweetapp.tweets.exception.authentication.AuthorizationException;
import com.tweetapp.tweets.exception.comment.CommentActionNotAuthorized;
import com.tweetapp.tweets.exception.comment.CommentNotFoundException;
import com.tweetapp.tweets.exception.tweet.TweetNotFoundException;
import com.tweetapp.tweets.model.comment.CommentRequest;
import com.tweetapp.tweets.model.tweet.TweetRequest;
import com.tweetapp.tweets.service.comment.CommentServiceImpl;
import com.tweetapp.tweets.util.UserHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
public class CommentController {
    static final String UNAUTHORIZED = "UNAUTHORIZED_REQUEST";
    @Autowired
    private AuthenticationController authenticationController;

    @Autowired
    private CommentServiceImpl commentService;

    @Autowired
    private UserHelper userHelper;

    @PostMapping("/{username}/reply/{id}")
    public String addComment(@PathVariable("username") String username, @PathVariable("id") Long tweetId, @RequestBody @Valid CommentRequest commentRequest, @RequestHeader(value = "Authorization", required = true) String requestTokenHeader) throws AuthorizationException, TweetNotFoundException {
        if (authenticationController.authorizeRequest(requestTokenHeader) && userHelper.getUsernameFromRequestHeader(requestTokenHeader).equals(username)) {
            return commentService.addComment(commentRequest, tweetId, requestTokenHeader);
        } else {
            throw new AuthorizationException(UNAUTHORIZED);
        }
    }

    @DeleteMapping("/{username}/reply/{id}")
    public String deleteComment(@PathVariable("username") String username, @PathVariable("id") Long tweetId, @RequestHeader(value = "Authorization", required = true) String requestTokenHeader) throws AuthorizationException, TweetNotFoundException, CommentActionNotAuthorized, CommentNotFoundException {
        if (authenticationController.authorizeRequest(requestTokenHeader) && userHelper.getUsernameFromRequestHeader(requestTokenHeader).equals(username)) {
            return commentService.deleteComment(tweetId, requestTokenHeader);
        } else {
            throw new AuthorizationException(UNAUTHORIZED);
        }
    }
}
