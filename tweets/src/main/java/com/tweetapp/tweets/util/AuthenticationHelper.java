package com.tweetapp.tweets.util;

import com.tweetapp.tweets.exception.authentication.AuthorizationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthenticationHelper {

    @Autowired
    private AuthenticationManager authenticationManager;

    public Authentication authenticate(String username, String password) throws AuthorizationException {
        try {
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new AuthorizationException("USER_DISABLED");
        } catch (BadCredentialsException e) {
            throw new AuthorizationException("INVALID_CREDENTIALS");
        }
    }
}
