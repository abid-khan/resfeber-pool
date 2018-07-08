package com.resfeber.pool.api.exception.mapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.resfeber.pool.api.ws.ResponseWS;
import com.resfeber.pool.service.exception.UserNotFoundException;

@Provider
public class UserNotFoundExceptionMapper implements ExceptionMapper<UserNotFoundException> {
    @Override
    public Response toResponse(UserNotFoundException e) {
        return Response.status(404).entity(ResponseWS.builder().code(404).message("NOT_FOUND").data(e.getMessage()).build()).build();
    }
}
