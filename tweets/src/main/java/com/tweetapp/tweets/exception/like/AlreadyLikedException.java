package com.tweetapp.tweets.exception.like;

public class AlreadyLikedException extends Exception {
    public AlreadyLikedException(String msg) {
        super(msg);
    }
}
