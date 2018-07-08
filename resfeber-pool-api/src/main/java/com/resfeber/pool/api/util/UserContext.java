package com.resfeber.pool.api.util;


import static org.springframework.security.core.context.SecurityContextHolder.getContext;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;

import com.resfeber.pool.service.UserService;
import com.resfeber.pool.service.bean.UserBean;

@Component
public class UserContext {
    @Autowired
    private UserService userService;

    public Optional<UserBean> getCurrentUser() {
        if (getContext().getAuthentication() instanceof OAuth2Authentication) {
            OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) getContext().getAuthentication();
            Map<String, String> detail = ((Map<String, String>) oAuth2Authentication.getUserAuthentication().getDetails());
            return Optional.ofNullable(userService.findByEmail(detail.get("email")));
        }
        return Optional.empty();
    }
}
