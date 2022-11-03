package com.tweetapp.tweets.controller.tweet;

import com.tweetapp.tweets.config.JwtTokenUtil;
import com.tweetapp.tweets.controller.authentication.AuthenticationController;
import com.tweetapp.tweets.exception.authentication.AuthorizationException;
import com.tweetapp.tweets.exception.like.AlreadyLikedException;
import com.tweetapp.tweets.exception.tweet.TweetNotFoundException;
import com.tweetapp.tweets.model.comment.CommentRequest;
import com.tweetapp.tweets.service.like.LikeServiceImpl;
import com.tweetapp.tweets.util.UserHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
public class LikeController {
    static final String UNAUTHORIZED = "UNAUTHORIZED_REQUEST";
    @Autowired
    private AuthenticationController authenticationController;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private LikeServiceImpl likeService;

    @Autowired
    private UserHelper userHelper;

    @PostMapping("/{username}/like/{id}")
    public String addLike(@PathVariable("username") String username, @PathVariable("id") Long tweetId, @RequestHeader(value = "Authorization", required = true) String requestTokenHeader) throws AuthorizationException, TweetNotFoundException, AlreadyLikedException {
        if (authenticationController.authorizeRequest(requestTokenHeader) && userHelper.getUsernameFromRequestHeader(requestTokenHeader).equals(username)) {
            return likeService.addLike(tweetId, requestTokenHeader);
        } else {
            throw new AuthorizationException(UNAUTHORIZED);
        }
    }

    @DeleteMapping("/{username}/like/{id}")
    public String deleteLike(@PathVariable("username") String username, @PathVariable("id") Long tweetId, @RequestHeader(value = "Authorization", required = true) String requestTokenHeader) throws AuthorizationException, TweetNotFoundException {
        if (authenticationController.authorizeRequest(requestTokenHeader) && userHelper.getUsernameFromRequestHeader(requestTokenHeader).equals(username)) {
            return likeService.deleteLike(tweetId, requestTokenHeader);
        } else {
            throw new AuthorizationException(UNAUTHORIZED);
        }
    }
}
