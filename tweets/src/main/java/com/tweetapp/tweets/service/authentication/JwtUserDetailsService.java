package com.tweetapp.tweets.service.authentication;

import com.tweetapp.tweets.exception.authentication.UsernameAlreadyExistsException;
import com.tweetapp.tweets.exception.authentication.UsernameNotExistsException;
import com.tweetapp.tweets.model.authentication.JwtRegisterRequest;
import com.tweetapp.tweets.model.authentication.ResetPassword;
import com.tweetapp.tweets.model.authentication.UserDetailsResponse;

import java.util.List;

public interface JwtUserDetailsService {

    public String addUser(JwtRegisterRequest jwtRegisterRequest) throws Exception;

    public String forgotPassword(String username) throws Exception;

    public String resetPassword(ResetPassword resetPassword) throws Exception;

    public List<UserDetailsResponse> getAllUser() throws Exception;

    public UserDetailsResponse searchUserByUsername(String username) throws UsernameNotExistsException;
}
