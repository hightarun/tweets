package com.tweetapp.tweets.service.tweet;

import com.tweetapp.tweets.exception.authentication.UsernameNotExistsException;
import com.tweetapp.tweets.exception.tweet.TweetNotAuthorizedException;
import com.tweetapp.tweets.exception.tweet.TweetNotFoundException;
import com.tweetapp.tweets.model.tweet.Tweet;
import com.tweetapp.tweets.model.tweet.TweetRequest;
import com.tweetapp.tweets.model.tweet.TweetResponse;

import java.util.List;

public interface TweetService {

    public String addTweets(TweetRequest tweetRequest, String token);

    public String updateTweets(TweetRequest tweetRequest, String token, Long id) throws UsernameNotExistsException, TweetNotFoundException, TweetNotAuthorizedException;

    public String deleteTweet(String token, Long id) throws UsernameNotExistsException, TweetNotFoundException, TweetNotAuthorizedException;

    public List<TweetResponse> getAllTweetsUser(String username);

    public List<TweetResponse> getAllTweet();
}
