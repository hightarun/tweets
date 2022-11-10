package com.tweetapp.tweets.model.authentication;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResetPassword {
    @Pattern(regexp = "[0-9]{6}",
            message = "Code must be of 6 digits")
    @NotBlank(message = "Code is required")
    private String code;
    
    @Pattern(regexp = "^[a-zA-Z]\\w{3,}+",
            message = "Username is invalid")
    @NotBlank(message = "Username is required")
    private String username;

    @Size(min = 8, message = "Password should contain at least 2 characters")
    @NotBlank(message = "Password is required")
    private String password;
}
