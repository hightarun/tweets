package com.tweetapp.tweets.model.tweet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TweetUser {
    private Long user_id;

    private String firstName;

    private String lastName;

    private String username;
}
