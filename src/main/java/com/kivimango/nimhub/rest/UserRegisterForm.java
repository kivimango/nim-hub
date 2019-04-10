package com.kivimango.nimhub.rest;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

final class UserRegisterForm {

    @NotNull(message = "You must supply a username")
    @Length(min = 3, max = 100, message = "The username must be at least {min} and max {max} characters long")
    private String username;

    @NotNull(message = "You must supply a password")
    @Length(min = 3, max = 255, message = "The password must be at least {min} and max {max} characters long")
    private String password;

    @NotNull(message = "You must supply an email address")
    @Length(min = 3, max = 255, message = "The email must be at least {min} and max {max} characters long")
    @Email(message = "The email address must be a valid RFC822 email address")
    private String email;

    String getUsername() {
         return username;
    }

    public void setUsername(String username) {
         this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
