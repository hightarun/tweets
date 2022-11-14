package com.tweetapp.tweets.controller.tweet;

import com.tweetapp.tweets.exception.authentication.AuthorizationException;
import com.tweetapp.tweets.exception.like.AlreadyLikedException;
import com.tweetapp.tweets.exception.tweet.TweetNotFoundException;
import com.tweetapp.tweets.service.like.LikeServiceImpl;
import com.tweetapp.tweets.util.AuthenticationHelper;
import com.tweetapp.tweets.util.UserHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class LikeController {
    static final String UNAUTHORIZED = "UNAUTHORIZED_REQUEST";
    private final LikeServiceImpl likeService;
    private final UserHelper userHelper;

    private final AuthenticationHelper authenticationHelper;

    @Autowired
    public LikeController(LikeServiceImpl likeService, UserHelper userHelper, AuthenticationHelper authenticationHelper) {
        this.likeService = likeService;
        this.userHelper = userHelper;
        this.authenticationHelper = authenticationHelper;
    }

    @PostMapping("/{username}/like/{id}")
    public String addLike(@PathVariable("username") String username, @PathVariable("id") Long tweetId, @RequestHeader(value = "Authorization", required = true) String requestTokenHeader) throws AuthorizationException, TweetNotFoundException, AlreadyLikedException {
        if (authenticationHelper.authorizeRequest(requestTokenHeader) && userHelper.getUsernameFromRequestHeader(requestTokenHeader).equals(username)) {
            return likeService.addLike(tweetId, requestTokenHeader);
        } else {
            throw new AuthorizationException(UNAUTHORIZED);
        }
    }

    @DeleteMapping("/{username}/like/{id}")
    public String deleteLike(@PathVariable("username") String username, @PathVariable("id") Long tweetId, @RequestHeader(value = "Authorization", required = true) String requestTokenHeader) throws AuthorizationException, TweetNotFoundException {
        if (authenticationHelper.authorizeRequest(requestTokenHeader) && userHelper.getUsernameFromRequestHeader(requestTokenHeader).equals(username)) {
            return likeService.deleteLike(tweetId, requestTokenHeader);
        } else {
            throw new AuthorizationException(UNAUTHORIZED);
        }
    }
}
