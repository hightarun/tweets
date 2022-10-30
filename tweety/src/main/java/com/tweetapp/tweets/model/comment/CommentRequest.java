package com.tweetapp.tweets.model.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequest {

    @Size(min = 2, message = "Comment should contain at least 2 characters")
    @Size(max = 144, message = "Comment should be of maximum 144 characters")
    @NotBlank(message = "Content is required")
    private String comment;
}
