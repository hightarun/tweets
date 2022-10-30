package com.tweetapp.tweets.service.like;

import com.tweetapp.tweets.exception.like.AlreadyLikedException;
import com.tweetapp.tweets.exception.tweet.TweetNotFoundException;

public interface LikeService {
    public String addLike(Long tweetId, String token) throws TweetNotFoundException, AlreadyLikedException;

    public String deleteLike(Long tweetId, String token) throws TweetNotFoundException;
}
