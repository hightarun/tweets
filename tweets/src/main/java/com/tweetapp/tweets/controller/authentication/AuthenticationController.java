package com.tweetapp.tweets.controller.authentication;

import com.tweetapp.tweets.config.JwtTokenUtil;
import com.tweetapp.tweets.exception.authentication.AuthorizationException;
import com.tweetapp.tweets.exception.authentication.UsernameNotExistsException;
import com.tweetapp.tweets.model.authentication.*;
import com.tweetapp.tweets.service.authentication.JwtUserDetailsServiceImpl;
import com.tweetapp.tweets.util.AuthenticationHelper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController()
@CrossOrigin
public class AuthenticationController {

    private final JwtTokenUtil jwtTokenUtil;
    private final JwtUserDetailsServiceImpl jwtUserDetailsService;
    private final AuthenticationHelper authenticationHelper;

    @Autowired
    public AuthenticationController(JwtTokenUtil jwtTokenUtil, JwtUserDetailsServiceImpl jwtUserDetailsService, AuthenticationHelper authenticationHelper) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.authenticationHelper = authenticationHelper;
    }


    @PostMapping(value = "/register")
    public String registerUser(@RequestBody @Valid JwtRegisterRequest jwtRegisterRequest) throws Exception {
        return jwtUserDetailsService.addUser(jwtRegisterRequest);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<JwtResponse> createToken(@RequestBody @Valid JwtLoginRequest authenticationRequest) throws AuthorizationException {
        Authentication auth = authenticationHelper.authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        if (!auth.isAuthenticated()) {
            throw new AuthorizationException("USER NOT AUTHENTICATED");
        }
        final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @GetMapping(value = "/authorize")
    public UserDetailsResponse authorizeRequest(@RequestHeader(value = "Authorization", required = true) String requestTokenHeader) {
        String jwtToken = null;
        String username = null;
        UserDetailsResponse user = null;
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                username = jwtTokenUtil.getUsernameFromToken(jwtToken);
                if (Boolean.TRUE.equals(jwtTokenUtil.isTokenExpired(jwtToken))) {
                    return null;
                }
                user = jwtUserDetailsService.searchUserByUsername(username);
            } catch (IllegalArgumentException | ExpiredJwtException | SignatureException |
                     UsernameNotExistsException e) {
                throw new RuntimeException(e);
            }
        }
        return user;
    }


    @GetMapping(value = "/{username}/forgot")
    public String forgotPassword(@PathVariable("username") String username) throws Exception {
        return jwtUserDetailsService.forgotPassword(username);
    }

    @PostMapping(value = "/reset-password")
    public String resetPassword(@RequestBody @Valid ResetPassword resetPassword) throws Exception {
        return jwtUserDetailsService.resetPassword(resetPassword);
    }

    @GetMapping("/users/all")
    public List<UserDetailsResponse> getAllUsers() throws Exception {
        return jwtUserDetailsService.getAllUser();
    }

    @GetMapping("/user/search/{username}")
    public UserDetailsResponse searchUserByUsername(@PathVariable("username") String username) throws UsernameNotExistsException {
        return jwtUserDetailsService.searchUserByUsername(username);
    }


    @GetMapping(value = "/")
    public ResponseEntity<String> check() {
        return new ResponseEntity<>("Auth-OK", HttpStatus.UNAUTHORIZED);
    }
}
