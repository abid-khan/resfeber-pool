package com.resfeber.pool.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.jaxrs.config.BeanConfig;

@Configuration
public class SwaggerConfig {
    @Bean
    public BeanConfig swaggerConfiguration() {
        final BeanConfig beanConfig = new BeanConfig();
        beanConfig.setResourcePackage("com.resfeber.pool.api.controller");
        beanConfig.setScan(true);
        beanConfig.setPrettyPrint(true);
        return beanConfig;
    }
}

