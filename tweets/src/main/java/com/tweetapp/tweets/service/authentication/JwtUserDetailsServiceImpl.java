package com.tweetapp.tweets.service.authentication;

import com.tweetapp.tweets.exception.authentication.UsernameAlreadyExistsException;
import com.tweetapp.tweets.exception.authentication.UsernameNotExistsException;
import com.tweetapp.tweets.model.authentication.JwtRegisterRequest;
import com.tweetapp.tweets.model.authentication.JwtUserDetails;
import com.tweetapp.tweets.model.authentication.User;
import com.tweetapp.tweets.model.authentication.UserDetailsResponse;
import com.tweetapp.tweets.repository.UserRepository;
import com.tweetapp.tweets.util.DtoConverter;
import com.tweetapp.tweets.util.EmailSenderService;
import com.tweetapp.tweets.util.PasswordEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class JwtUserDetailsServiceImpl implements UserDetailsService, JwtUserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final DtoConverter dtoConverter;
    private final EmailSenderService emailSenderService;

    @Autowired
    public JwtUserDetailsServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, DtoConverter dtoConverter, EmailSenderService emailSenderService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.dtoConverter = dtoConverter;
        this.emailSenderService = emailSenderService;
    }


    public UserDetails loadUserByUsername(String loginId) {
        User user = userRepository.findByUsername(loginId);

        if (user == null) {
            throw new UsernameNotFoundException("User not found with username " + loginId);
        }
        log.info("User found");
        log.info("User successfully located");
        // JwtUserDetails implements UserDetails
        return new JwtUserDetails(user);
    }

    public String addUser(JwtRegisterRequest jwtRegisterRequest) throws UsernameAlreadyExistsException {
        if (userRepository.findByUsername(jwtRegisterRequest.getUsername()) != null) {
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

    @Override
    public String forgotPassword(String username) throws UsernameNotExistsException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotExistsException("Username " + username + " does not exists.");
        }
        String email = user.getEmail();
        emailSenderService.sendSimpleEmail("tarunbisht252000@gmail.com", "Test Subject", "Test Body");
        return "Password reset link has been sent to the registered email";
    }

    @Override
    public List<UserDetailsResponse> getAllUser() {
        return userRepository.findAll().stream().map(user -> dtoConverter.convertToUserDetailsResponse(user)).collect(Collectors.toList());
    }

    @Override
    public UserDetailsResponse searchUserByUsername(String username) throws UsernameNotExistsException {
        User foundUser = userRepository.findByUsername(username);
        if (foundUser == null) {
            throw new UsernameNotExistsException("User with username " + username + " does not exists");
        }
        return dtoConverter.convertToUserDetailsResponse(foundUser);
    }

}
