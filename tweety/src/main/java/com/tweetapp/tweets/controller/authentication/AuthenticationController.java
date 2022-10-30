package com.tweetapp.tweets.controller.authentication;

import com.tweetapp.tweets.config.JwtTokenUtil;
import com.tweetapp.tweets.exception.authentication.AuthorizationException;
import com.tweetapp.tweets.exception.authentication.UsernameAlreadyExistsException;
import com.tweetapp.tweets.exception.authentication.UsernameNotExistsException;
import com.tweetapp.tweets.model.authentication.JwtLoginRequest;
import com.tweetapp.tweets.model.authentication.JwtRegisterRequest;
import com.tweetapp.tweets.model.authentication.JwtResponse;
import com.tweetapp.tweets.model.authentication.UserDetailsResponse;
import com.tweetapp.tweets.service.authentication.JwtUserDetailsServiceImpl;
import com.tweetapp.tweets.util.AuthenticationHelper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.util.List;

@RestController()
@CrossOrigin
public class AuthenticationController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsServiceImpl jwtUserDetailsService;

    @Autowired
    private AuthenticationHelper authenticationHelper;

    @PostMapping(value = "/register")
    public String registerUser(@RequestBody @Valid JwtRegisterRequest jwtRegisterRequest) throws UsernameAlreadyExistsException {
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

    @PostMapping(value = "/authorize")
    public boolean authorizeRequest(@RequestHeader(value = "Authorization", required = true) String requestTokenHeader) {
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

    @GetMapping(value = "/{username}/forgot")
    public String forgotPassword(@PathVariable("username") String username) throws UsernameNotExistsException {
        return jwtUserDetailsService.forgotPassword(username);
    }

    @GetMapping("/users/all")
    public List<UserDetailsResponse> getAllUsers() {
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
