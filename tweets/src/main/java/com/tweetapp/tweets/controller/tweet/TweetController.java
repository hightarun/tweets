package com.tweetapp.tweets.controller.tweet;

import com.tweetapp.tweets.config.JwtTokenUtil;
import com.tweetapp.tweets.controller.authentication.AuthenticationController;
import com.tweetapp.tweets.exception.authentication.AuthorizationException;
import com.tweetapp.tweets.exception.authentication.UsernameNotExistsException;
import com.tweetapp.tweets.exception.tweet.TweetNotAuthorizedException;
import com.tweetapp.tweets.exception.tweet.TweetNotFoundException;
import com.tweetapp.tweets.model.tweet.Tweet;
import com.tweetapp.tweets.model.tweet.TweetRequest;
import com.tweetapp.tweets.model.tweet.TweetResponse;
import com.tweetapp.tweets.service.tweet.TweetServiceImpl;
import com.tweetapp.tweets.util.UserHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
public class TweetController {

    static final String UNAUTHORIZED = "UNAUTHORIZED_REQUEST";

    @Autowired
    private AuthenticationController authenticationController;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private TweetServiceImpl tweetService;

    @Autowired
    private UserHelper userHelper;

    @PostMapping("/{username}/add")
    public String addTweet(@PathVariable("username") String username, @RequestBody @Valid TweetRequest tweetRequest, @RequestHeader(value = "Authorization", required = true) String requestTokenHeader) throws AuthorizationException {
        if (authenticationController.authorizeRequest(requestTokenHeader) && userHelper.getUsernameFromRequestHeader(requestTokenHeader).equals(username)) {
            return tweetService.addTweets(tweetRequest, requestTokenHeader);
        } else {
            throw new AuthorizationException(UNAUTHORIZED);
        }
    }

    @PutMapping("/{username}/update/{id}")
    public String updateTweet(@PathVariable("username") String username, @PathVariable("id") long id, @RequestBody @Valid TweetRequest tweetRequest, @RequestHeader(value = "Authorization", required = true) String requestTokenHeader) throws AuthorizationException, UsernameNotExistsException, TweetNotAuthorizedException, TweetNotFoundException {
        if (authenticationController.authorizeRequest(requestTokenHeader) && userHelper.getUsernameFromRequestHeader(requestTokenHeader).equalsIgnoreCase(username)) {
            return tweetService.updateTweets(tweetRequest, requestTokenHeader, id);
        } else {
            throw new AuthorizationException(UNAUTHORIZED);
        }
    }

    @DeleteMapping("/{username}/delete/{id}")
    public String deleteTweet(@PathVariable("username") String username, @PathVariable("id") long id, @RequestHeader(value = "Authorization", required = true) String requestTokenHeader) throws AuthorizationException, UsernameNotExistsException, TweetNotFoundException, TweetNotAuthorizedException {
        if (authenticationController.authorizeRequest(requestTokenHeader) && userHelper.getUsernameFromRequestHeader(requestTokenHeader).equals(username)) {
            return tweetService.deleteTweet(requestTokenHeader, id);
        } else {
            throw new AuthorizationException(UNAUTHORIZED);
        }
    }

    @GetMapping("/{username}")
    public List<TweetResponse> getALlTweetsUser(@PathVariable("username") String username) {
        return tweetService.getAllTweetsUser(username);
    }

    @GetMapping("/all")
    public List<TweetResponse> getAllTweet() {
        return tweetService.getAllTweet();
    }
}