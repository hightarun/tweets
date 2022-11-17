package com.tweetapp.tweets.service.like;

import com.tweetapp.tweets.exception.tweet.TweetNotFoundException;

public interface LikeService {
    public String addLike(Long tweetId, String token) throws TweetNotFoundException;

    public String deleteLike(Long tweetId, String token) throws TweetNotFoundException;
}
