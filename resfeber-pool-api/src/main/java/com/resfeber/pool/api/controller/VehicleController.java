package com.resfeber.pool.api.controller;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.resfeber.pool.api.mapper.VehicleWSMapper;
import com.resfeber.pool.api.ws.ResponseWS;
import com.resfeber.pool.api.ws.VehicleWS;
import com.resfeber.pool.core.type.Status;
import com.resfeber.pool.service.VehicleService;
import com.resfeber.pool.service.bean.VehicleBean;

@Slf4j
@Component
@Path("/vehicles")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private VehicleWSMapper vehicleWSMapper;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAllVehicles() {

        List<VehicleBean> vehicleBeans = vehicleService.findByStatus(Status.ACTIVE);
        List<VehicleWS> vehicles = vehicleBeans.stream().filter(p -> Objects.nonNull(p)).map(p -> vehicleWSMapper.fromSource(p)).collect(Collectors.toList());
        return Response.ok().entity(ResponseWS.builder().code(Response.Status.OK.getStatusCode()).message(Response.Status.OK.getReasonPhrase()).data(vehicles).build()).build();

    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerVehicle(VehicleWS vehicle) {

        VehicleBean vehicleBean = vehicleService.saveOrUpdate(vehicleWSMapper.toSource(vehicle));
        return Response.status(201).entity(ResponseWS.builder().code(201).message("CREATED").data(vehicleWSMapper.fromSource(vehicleBean)).build()).build();
    }
}
