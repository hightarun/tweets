package com.tweetapp.tweets.util;

import com.tweetapp.tweets.config.JwtTokenUtil;
import com.tweetapp.tweets.exception.authentication.AuthorizationException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

@Service
@Slf4j
public class AuthenticationHelper {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenUtil jwtTokenUtil;

    public AuthenticationHelper(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public Authentication authenticate(String username, String password) throws AuthorizationException {
        try {
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new AuthorizationException("USER_DISABLED");
        } catch (BadCredentialsException e) {
            throw new AuthorizationException("INVALID_CREDENTIALS");
        }
    }

    public boolean authorizeRequest(String requestTokenHeader) {
        String jwtToken = null;
        String username = null;
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                username = jwtTokenUtil.getUsernameFromToken(jwtToken);
                if (Boolean.TRUE.equals(jwtTokenUtil.isTokenExpired(jwtToken))) {
                    return false;
                }
            } catch (IllegalArgumentException | ExpiredJwtException | SignatureException e) {
                return false;
            }
        }
        return username != null;
    }
}
