package com.resfeber.pool.service.security;

import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

import org.springframework.security.core.GrantedAuthority;

import com.resfeber.pool.service.bean.UserBean;

@Setter
@Getter
public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {
    private UserBean user;

    public UserDetails() {

    }

    public UserDetails(UserBean user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
