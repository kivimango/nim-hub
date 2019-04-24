package com.kivimango.nimhub.rest;

import com.kivimango.nimhub.data.User;
import com.kivimango.nimhub.data.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
class AuthService implements UserDetailsService {

    private final UserRepository users;

    AuthService(UserRepository users) {
        this.users = users;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = users.findByUsername(username);
        if(user.isPresent()) {
            return new CurrentUser(user.get());
        }
        throw new UsernameNotFoundException(username);
    }
}
