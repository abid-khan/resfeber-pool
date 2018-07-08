package com.resfeber.pool.api.config;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.resfeber.pool.service.UserService;
import com.resfeber.pool.service.bean.UserBean;


public class OAuth2ClientAuthenticationProcessingFilterImpl extends OAuth2ClientAuthenticationProcessingFilter {
    private UserService userService;

    public OAuth2ClientAuthenticationProcessingFilterImpl(String defaultFilterProcessesUrl, UserService userService) {
        super(defaultFilterProcessesUrl);
        this.userService = userService;
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
        saveOrUpdateUser();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveOrUpdateUser() {

        OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) getContext().getAuthentication();
        Map<String, String> detail = ((Map<String, String>) oAuth2Authentication.getUserAuthentication().getDetails());

        UserBean user = UserBean.builder().externalId(detail.get("sub")).firstName(detail.get("name")).lastName(detail.get("family_name")).givenName(detail.get("given_name")).email(detail.get("email")).build();
        UserBean existingUser = userService.findByEmail(user.getEmail());
        if (Objects.nonNull(existingUser)) {
            return;
        }

        userService.saveOrUpdate(user);
    }
}
