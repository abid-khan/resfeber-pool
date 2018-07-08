package com.resfeber.pool.data.config;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.resfeber.pool.core.type.Status;
import com.resfeber.pool.data.model.User;
import com.resfeber.pool.data.repository.UserRepository;


@Component
public class AuditorAwareImpl implements AuditorAware<User> {

    @Autowired
    UserRepository userRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public Optional<User> getCurrentAuditor() {
        if (getContext().getAuthentication() instanceof OAuth2Authentication) {
            OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) getContext().getAuthentication();
            if (oAuth2Authentication.isAuthenticated()) {
                Map<String, String> detail = ((Map<String, String>) oAuth2Authentication.getUserAuthentication().getDetails());
                User user = userRepository.findByEmailAndStatus(detail.get("email"), Status.ACTIVE);
                return Optional.ofNullable(user);
            }
        }
        return Optional.empty();
    }
}
