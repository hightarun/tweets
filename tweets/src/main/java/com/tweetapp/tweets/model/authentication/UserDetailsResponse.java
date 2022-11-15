package com.tweetapp.tweets.model.authentication;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsResponse {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String username;

}
