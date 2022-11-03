package com.tweetapp.tweets.model.tweet;

import com.tweetapp.tweets.model.comment.CommentResponse;
import com.tweetapp.tweets.model.like.Like;
import com.tweetapp.tweets.model.like.LikeResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TweetResponse {

    private Long id;

    private String content;

    private TweetUser tweetUser;

    private List<CommentResponse> comments;

    private List<LikeResponse> likes;
}
