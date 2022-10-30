package com.tweetapp.tweets.exception.tweet;

public class TweetNotAuthorizedException extends Exception {
    public TweetNotAuthorizedException(String msg) {
        super(msg);
    }
}
