package com.tweetapp.tweets.model.authentication;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtRegisterRequest implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;

    @Pattern(regexp = "[a-zA-Z]+",
            message = "First Name is invalid")
    @NotBlank(message = "First Name is required")
    private String firstName;

    @Pattern(regexp = "[a-zA-Z]+",
            message = "Last Name is invalid")
    @NotBlank(message = "Last name is required")
    private String lastName;

    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,10}",
            message = "Email is invalid")
    @NotBlank(message = "Email is required")
    private String email;

    @Pattern(regexp = "^[a-zA-Z]\\w{3,}+",
            message = "Username is invalid")
    @NotBlank(message = "Username is required")
    private String username;

    @Size(min = 8, message = "Password should contain at least 2 characters")
    @NotBlank(message = "Password is required")
    private String password;

    @Pattern(regexp = "[0-9]{10,11}",
            message = "Phone no. is invalid")
    @NotBlank(message = "Contact no. is required")
    private String contactNumber;

}
