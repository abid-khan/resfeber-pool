package com.resfeber.pool.service.impl;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

import com.resfeber.pool.service.UserService;
import com.resfeber.pool.service.bean.UserBean;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserBean user = null;

        if (getContext().getAuthentication() instanceof OAuth2Authentication) {
            OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) getContext().getAuthentication();
            Map<String, String> detail = ((Map<String, String>) oAuth2Authentication.getUserAuthentication().getDetails());
            userService.findByEmail(detail.get("email"));
        }

        return new com.resfeber.pool.service.security.UserDetails(user);
    }
}
