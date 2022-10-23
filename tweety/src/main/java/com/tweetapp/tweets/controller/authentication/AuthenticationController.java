package com.tweetapp.tweets.controller.authentication;

import com.tweetapp.tweets.config.JwtTokenUtil;
import com.tweetapp.tweets.exception.authentication.AuthorizationException;
import com.tweetapp.tweets.exception.authentication.UsernameAlreadyExistsException;
import com.tweetapp.tweets.model.authentication.JwtLoginRequest;
import com.tweetapp.tweets.model.authentication.JwtRegisterRequest;
import com.tweetapp.tweets.model.authentication.JwtResponse;
import com.tweetapp.tweets.service.authentication.JwtUserDetailsService;
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

import javax.validation.Valid;

@RestController()
@CrossOrigin
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @PostMapping(value="/register")
    public String registerUser(@RequestBody @Valid JwtRegisterRequest jwtRegisterRequest) throws UsernameAlreadyExistsException {
        return jwtUserDetailsService.addUser(jwtRegisterRequest);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<JwtResponse> createToken(@RequestBody @Valid JwtLoginRequest authenticationRequest) throws AuthorizationException{
        Authentication auth = authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        if(!auth.isAuthenticated()){
            throw new AuthorizationException("USER NOT AUTHENTICATED");
        }
        final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping(value="/authorize")
    public boolean authorizeRequest(@RequestHeader(value="Authorization",required = true) String requestTokenHeader){
        String jwtToken = null;
        String username = null;
        if(requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")){
            jwtToken = requestTokenHeader.substring(7);
            try{
                username = jwtTokenUtil.getUsernameFromToken(jwtToken);
                if(Boolean.TRUE.equals(jwtTokenUtil.isTokenExpired(jwtToken))){
                    return false;
                }
            }catch(IllegalArgumentException | ExpiredJwtException | SignatureException e){
                return false;
            }
        }
        return username != null;
    }

    @GetMapping(value="/{username}/forgot")
    public String forgotPassword(@PathVariable("username") String username){
        
        return null;
    }

    private Authentication authenticate(String username , String password) throws AuthorizationException {
        try{
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
        }catch(DisabledException e){
            throw new AuthorizationException("USER_DISABLED");
        }catch(BadCredentialsException e){
            throw new AuthorizationException("INVALID_CREDENTIALS");
        }
    }

    @GetMapping(value = "/")
    public ResponseEntity<String> check() {
        return new ResponseEntity<>("Auth-OK", HttpStatus.UNAUTHORIZED);
    }
}
