package com.resfeber.pool.api.config;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

import com.resfeber.pool.api.controller.PoolsController;
import com.resfeber.pool.api.controller.UserController;
import com.resfeber.pool.api.controller.VehicleController;
import com.resfeber.pool.api.exception.mapper.GenericExceptionMapper;
import com.resfeber.pool.api.exception.mapper.PoolServiceExceptionMapper;
import com.resfeber.pool.api.exception.mapper.UserNotFoundExceptionMapper;
import com.resfeber.pool.api.exception.mapper.VehicleServiceExceptionMapper;

@Configuration
@ApplicationPath("/api")
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        // Controllers
        register(PoolsController.class);
        register(VehicleController.class);
        register(UserController.class);
        // Exception Mapper
        register(UserNotFoundExceptionMapper.class);
        register(GenericExceptionMapper.class);
        register(PoolServiceExceptionMapper.class);
        register(VehicleServiceExceptionMapper.class);
        //Package
        packages("com.resfeber.pool");
        //Swagger
        register(io.swagger.jaxrs.listing.ApiListingResource.class);
        register(io.swagger.jaxrs.listing.SwaggerSerializers.class);
    }
}
