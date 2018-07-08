package com.resfeber.pool.data.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import com.resfeber.pool.data.model.User;

@Configuration
public class AuditorConfiguration {
    @Bean
    public AuditorAware<User> auditorAware(){
        return new AuditorAwareImpl();
    }
}
