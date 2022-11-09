package com.tweetapp.tweets.util;

import com.tweetapp.tweets.config.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserHelper {

    static final String UNAUTHORIZED = "UNAUTHORIZED_REQUEST";
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public UserHelper(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    // Helper function to extract token from request header
    public String getTokenFromRequestHeader(String requestTokenHeader) {
        String jwtToken = null;
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
        }
        return jwtToken;
    }

    public String getUsernameFromRequestHeader(String requestTokenHeader) {
        String username = null;
        String jwtToken = null;
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            username = jwtTokenUtil.getUsernameFromToken(jwtToken);
        }
        return username;
    }
}
