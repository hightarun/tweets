package com.tweetapp.tweets.exception.authentication;

public class UsernameAlreadyExistsException extends Exception{

    public UsernameAlreadyExistsException(String msg){
        super(msg);
    }
}
