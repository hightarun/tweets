package com.tweetapp.tweets.exception.authentication;

public class UsernameNotExistsException extends Exception {
    public UsernameNotExistsException(String msg) {
        super((msg));
    }
}
