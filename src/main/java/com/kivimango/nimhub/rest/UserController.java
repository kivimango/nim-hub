package com.kivimango.nimhub.rest;

import com.kivimango.nimhub.data.User;
import com.kivimango.nimhub.data.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.http.ResponseEntity.badRequest;

@RestController
final class UserController {

    private final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserRepository users;

    @Autowired
    PasswordEncoder passwordEncoder;

    UserController(UserRepository users) {
        this.users = users;
    }

    @GetMapping("/users")
    Iterable<User> listUsers() {
        return users.findAll();
    }

    @PostMapping(value = "/users", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    void register(@Valid UserRegisterForm form) throws UsernameAlreadyExists, EmailAddressAlreadyExists {
        boolean usernameTaken = users.existsByUsername(form.getUsername());
        boolean emailTaken = users.existsByEmail(form.getEmail());

        if(usernameTaken) {
            throw new UsernameAlreadyExists(form.getUsername());
        } else if(emailTaken) {
            throw new EmailAddressAlreadyExists(form.getEmail());
        } else {
            User user = new User();
            user.setUsername(form.getUsername());
            user.setPassword(passwordEncoder.encode(form.getPassword()));
            user.setEmail(form.getEmail());
            users.save(user);

            log.info("New user saved");
        }
    }

    @ExceptionHandler(BindException.class)
    ResponseEntity<Set<ValidationError>> handleFormValidationError(BindException ex) {
        Set<ValidationError> errors = new HashSet<>();

        for(FieldError error : ex.getFieldErrors()) {
            errors.add(new ValidationError(error.getField(), error.getDefaultMessage()));
        }

        return badRequest().body(errors);
    }

    @ExceptionHandler(UsernameAlreadyExists.class)
    ResponseEntity<ValidationError> handleUsernameAlreadyExists(UsernameAlreadyExists ex) {
        log.info("Responding with 401 for request that tried to register a new user with username {} already existing in the database.", ex.getUsername());
        return badRequest().body(new ValidationError("username", "This username already exists.Please choose another one"));
    }

    @ExceptionHandler(EmailAddressAlreadyExists.class)
    ResponseEntity<ValidationError> handleEmailAddressAlreadyExists(EmailAddressAlreadyExists ex) {
        log.info("Responding with 401 for request that tried to register a new user with email address {} already in use.", ex.getEmail());
        return badRequest().body(new ValidationError("email", "This email address already in use.Please choose another one"));
    }
}
