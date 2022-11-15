package com.tweetapp.tweets.model.comment;

import com.tweetapp.tweets.model.authentication.UserDetailsResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse {
    private Long id;
    private String content;
    private UserDetailsResponse commentUser;
    private Long commentTweetId;
}
