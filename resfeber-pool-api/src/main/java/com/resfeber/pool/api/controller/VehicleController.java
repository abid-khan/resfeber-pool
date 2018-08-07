package com.resfeber.pool.api.controller;

import java.util.Objects;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.resfeber.pool.api.mapper.UserWSMapper;
import com.resfeber.pool.api.mapper.VehicleWSMapper;
import com.resfeber.pool.api.util.UserContext;
import com.resfeber.pool.api.ws.ResponseWS;
import com.resfeber.pool.api.ws.VehicleWS;
import com.resfeber.pool.service.VehicleService;
import com.resfeber.pool.service.bean.UserBean;
import com.resfeber.pool.service.bean.VehicleBean;

@Slf4j
@Component
@Path("/vehicles")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private VehicleWSMapper vehicleWSMapper;
    @Autowired
    private UserContext userContext;
    @Autowired
    private UserWSMapper userWSMapper;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByUser() {
        UserBean userBean = userContext.getCurrentUser().orElse(null);
        VehicleWS vehicleWS = null;
        if (Objects.nonNull(userBean)) {
            VehicleBean vehicleBean = vehicleService.findByUser(userBean.getUuid());
            vehicleWS = vehicleWSMapper.fromSource(vehicleBean);
        }

        return Response.ok().entity(ResponseWS.builder().code(Response.Status.OK.getStatusCode()).message(Response.Status.OK.getReasonPhrase()).data(vehicleWS).build()).build();

    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerVehicle(VehicleWS vehicle) {
        UserBean userBean = userContext.getCurrentUser().orElse(null);
        vehicle.setUser(userWSMapper.fromSource(userBean));
        VehicleBean vehicleBean = vehicleService.saveOrUpdate(vehicleWSMapper.toSource(vehicle));
        return Response.status(201).entity(ResponseWS.builder().code(201).message("CREATED").data(vehicleWSMapper.fromSource(vehicleBean)).build()).build();
    }

    @DELETE
    @Path("/{uuid}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response registerVehicle(@PathParam("uuid") String uuid) {
        VehicleBean vehicleBean = vehicleService.findByUuid(uuid);
        vehicleService.delete(vehicleBean);
        return Response.status(204).entity(ResponseWS.builder().code(204).message("OK").data(null).build()).build();
    }
}
