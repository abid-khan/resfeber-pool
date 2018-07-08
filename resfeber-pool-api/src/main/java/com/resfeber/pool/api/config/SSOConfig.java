package com.resfeber.pool.api.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.CompositeFilter;

import com.resfeber.pool.service.UserService;

@ComponentScan({"com.resfeber.pool.service"})

@Configuration
@EnableOAuth2Client
public class SSOConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private OAuth2ClientContext oauth2ClientContext;
    @Autowired
    private UserService userService;
    @Value("${security.oauth2.client.clientId}")
    private String cliendId;
    @Value("${security.oauth2.client.clientSecret}")
    private String clientSecret;
    @Value("${security.oauth2.client.accessTokenUri}")
    private String accessTokenUri;
    @Value("${security.oauth2.client.userAuthorizationUri}")
    private String userAuthorizationUri;
    @Value("${security.oauth2.client.tokenName}")
    private String tokenName;
    @Value("${security.oauth2.client.authenticationScheme}")
    private AuthenticationScheme authenticationScheme;
    @Value("${security.oauth2.client.clientAuthenticationScheme}")
    private AuthenticationScheme clientAuthenticationScheme;
    @Value("${security.oauth2.client.scope}")
    private String[] scope;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests().antMatchers("/", "/login","/api/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
    }

    @Bean
    public Filter ssoFilter() {
        CompositeFilter filter = new CompositeFilter();
        List<Filter> filters = new ArrayList<>();

        OAuth2ClientAuthenticationProcessingFilter oauth2ClientAuthenticationProcessingFilter = new OAuth2ClientAuthenticationProcessingFilterImpl("/login", userService);
        OAuth2RestTemplate oauth2RestTemplate = new OAuth2RestTemplate(authorizationCodeResourceDetails(), oauth2ClientContext);
        oauth2ClientAuthenticationProcessingFilter.setRestTemplate(oauth2RestTemplate);
        UserInfoTokenServices tokenServices = new UserInfoTokenServices(resourceServerProperties().getUserInfoUri(), authorizationCodeResourceDetails().getClientId());
        tokenServices.setRestTemplate(oauth2RestTemplate);
        oauth2ClientAuthenticationProcessingFilter.setTokenServices(tokenServices);
        filters.add(oauth2ClientAuthenticationProcessingFilter);
        filter.setFilters(filters);
        return filter;
    }

    @Bean
    public AuthorizationCodeResourceDetails authorizationCodeResourceDetails() {
        AuthorizationCodeResourceDetails authorizationCodeResourceDetails = new AuthorizationCodeResourceDetails();
        authorizationCodeResourceDetails.setClientId(cliendId);
        authorizationCodeResourceDetails.setClientSecret(clientSecret);
        authorizationCodeResourceDetails.setAccessTokenUri(accessTokenUri);
        authorizationCodeResourceDetails.setUserAuthorizationUri(userAuthorizationUri);
        authorizationCodeResourceDetails.setTokenName(tokenName);
        authorizationCodeResourceDetails.setAuthenticationScheme(authenticationScheme);
        authorizationCodeResourceDetails.setClientAuthenticationScheme(clientAuthenticationScheme);
        authorizationCodeResourceDetails.setScope(Arrays.asList(scope[0], scope[1]));
        return authorizationCodeResourceDetails;
    }

    @Bean
    public ResourceServerProperties resourceServerProperties() {
        return new ResourceServerProperties();
    }

    @Bean
    public FilterRegistrationBean oauth2ClientFilterRegistration(
            OAuth2ClientContextFilter filter) {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(filter);
        registration.setOrder(-100);
        return registration;
    }
}
