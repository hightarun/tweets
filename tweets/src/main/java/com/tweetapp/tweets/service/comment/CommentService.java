package com.tweetapp.tweets.service.comment;

import com.tweetapp.tweets.exception.comment.CommentActionNotAuthorized;
import com.tweetapp.tweets.exception.comment.CommentNotFoundException;
import com.tweetapp.tweets.exception.tweet.TweetNotFoundException;
import com.tweetapp.tweets.model.comment.CommentRequest;

public interface CommentService {

    public String addComment(CommentRequest commentRequest, Long tweetId, String token) throws TweetNotFoundException;

    public String deleteComment(Long tweetId, String token) throws TweetNotFoundException, CommentNotFoundException, CommentActionNotAuthorized;
}
