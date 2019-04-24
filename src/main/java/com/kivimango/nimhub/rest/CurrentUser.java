package com.kivimango.nimhub.rest;

import com.kivimango.nimhub.data.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

final class CurrentUser implements UserDetails {

    private Long id;
    private String username, email, password;
    private Collection<? extends GrantedAuthority> authorities;
    private Boolean isNotBanned;

    CurrentUser(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.authorities = AuthorityUtils.createAuthorityList("USER");
        this.isNotBanned = !user.getBanned();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isNotBanned;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isNotBanned;
    }
}
