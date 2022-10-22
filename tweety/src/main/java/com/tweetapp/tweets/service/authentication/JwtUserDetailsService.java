package com.tweetapp.tweets.service.authentication;

import com.tweetapp.tweets.exception.authentication.EmailDoesNotExistsException;
import com.tweetapp.tweets.exception.authentication.UsernameAlreadyExistsException;
import com.tweetapp.tweets.model.authentication.JwtRegisterRequest;
import com.tweetapp.tweets.model.authentication.JwtUserDetails;
import com.tweetapp.tweets.model.authentication.User;
import com.tweetapp.tweets.repository.authentication.UserRepository;
import com.tweetapp.tweets.util.PasswordEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDetails loadUserByUsername(String loginId) {
        User user = userRepository.findByUsername(loginId);

        if(user == null){
            throw new UsernameNotFoundException("User not found with username " + loginId);
        }
        log.info("User found");
        log.info("User successfully located");
        // JwtUserDetails implements UserDetails
        return new JwtUserDetails(user);
    }

    public String addUser(JwtRegisterRequest jwtRegisterRequest) throws UsernameAlreadyExistsException {
        if(userRepository.findByUsername(jwtRegisterRequest.getUsername()) != null){
            throw new UsernameAlreadyExistsException("Username " + jwtRegisterRequest.getUsername() + " already exists.");
        }
        User newUser = new User();
        newUser.setFirstName(jwtRegisterRequest.getFirstName());
        newUser.setLastName(jwtRegisterRequest.getLastName());
        newUser.setEmail(jwtRegisterRequest.getEmail());
        newUser.setUsername(jwtRegisterRequest.getUsername());
        newUser.setContactNumber(jwtRegisterRequest.getContactNumber());
        newUser.setPassword(passwordEncoder.encoder().encode(jwtRegisterRequest.getPassword()));
        userRepository.save(newUser);
        log.info("User added in DB");
        return "User Registered";
    }

    public String forgotPassword(String email)throws EmailDoesNotExistsException {
        User user = userRepository.findByEmail(email);
        if(user == null){
            throw new EmailDoesNotExistsException("Email " +email +"is not registered with us.");
        }

    }

}
