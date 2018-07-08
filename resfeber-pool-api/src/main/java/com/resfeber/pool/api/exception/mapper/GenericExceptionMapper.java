package com.resfeber.pool.api.exception.mapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import lombok.extern.slf4j.Slf4j;

import com.resfeber.pool.api.ws.ResponseWS;

@Slf4j
@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {
    @Override
    public Response toResponse(Throwable throwable) {
        log.error("Failure due to {}", throwable);
        return Response.status(500).entity(ResponseWS.builder().code(500).message("INTERNAL_SERVER_ERROR").data(throwable.getMessage()).build()).build();
    }
}
