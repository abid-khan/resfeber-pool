package com.resfeber.pool.api.exception.mapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.resfeber.pool.api.ws.ResponseWS;
import com.resfeber.pool.service.exception.PoolServiceException;

@Provider
public class PoolServiceExceptionMapper implements ExceptionMapper<PoolServiceException> {
    @Override
    public Response toResponse(PoolServiceException e) {
        return Response.status(500).entity(ResponseWS.builder().code(500).message("INTERNAL_SERVER_ERROR").data(e.getMessage()).build()).build();
    }
}
