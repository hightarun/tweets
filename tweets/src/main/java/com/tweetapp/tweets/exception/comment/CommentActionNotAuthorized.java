package com.tweetapp.tweets.exception.comment;

public class CommentActionNotAuthorized extends Exception {
    public CommentActionNotAuthorized(String msg) {
        super(msg);
    }
}
