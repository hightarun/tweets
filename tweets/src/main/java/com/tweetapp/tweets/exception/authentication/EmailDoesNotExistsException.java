package com.tweetapp.tweets.exception.authentication;

public class EmailDoesNotExistsException extends Exception{
    public EmailDoesNotExistsException(String msg){
        super(msg);
    }
}
