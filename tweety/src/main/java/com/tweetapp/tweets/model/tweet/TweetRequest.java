package com.tweetapp.tweets.model.tweet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TweetRequest {
    @Size(min = 2, message = "Tweet should contain at least 2 characters")
    @Size(max = 144, message = "Tweet should be of maximum 144 characters")
    @NotBlank(message = "Content is required")
    private String content;
}
