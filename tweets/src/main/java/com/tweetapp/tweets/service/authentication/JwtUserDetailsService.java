package com.tweetapp.tweets.service.authentication;

import com.tweetapp.tweets.exception.TryCatchException;
import com.tweetapp.tweets.exception.authentication.InvalidResetCodeException;
import com.tweetapp.tweets.exception.authentication.UsernameAlreadyExistsException;
import com.tweetapp.tweets.exception.authentication.UsernameNotExistsException;
import com.tweetapp.tweets.model.authentication.JwtRegisterRequest;
import com.tweetapp.tweets.model.authentication.ResetPassword;
import com.tweetapp.tweets.model.authentication.UserDetailsResponse;

import java.util.List;

public interface JwtUserDetailsService {

    public String addUser(JwtRegisterRequest jwtRegisterRequest) throws UsernameAlreadyExistsException, TryCatchException;

    public String forgotPassword(String username) throws UsernameNotExistsException, TryCatchException;

    public String resetPassword(ResetPassword resetPassword) throws UsernameNotExistsException, InvalidResetCodeException, TryCatchException;

    public List<UserDetailsResponse> getAllUser() throws TryCatchException;

    public UserDetailsResponse searchUserByUsername(String username) throws UsernameNotExistsException;
}
