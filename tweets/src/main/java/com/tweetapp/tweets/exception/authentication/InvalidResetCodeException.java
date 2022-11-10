package com.tweetapp.tweets.exception.authentication;

public class InvalidResetCodeException extends Exception {
    public InvalidResetCodeException(String msg) {
        super(msg);
    }
}
