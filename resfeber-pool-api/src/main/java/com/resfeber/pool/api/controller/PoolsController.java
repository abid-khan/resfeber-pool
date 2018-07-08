package com.resfeber.pool.api.controller;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.resfeber.pool.api.mapper.PoolWSMapper;
import com.resfeber.pool.api.mapper.PoolerWSMapper;
import com.resfeber.pool.api.util.UserContext;
import com.resfeber.pool.api.ws.PoolWS;
import com.resfeber.pool.api.ws.ResponseWS;
import com.resfeber.pool.core.type.PaymentStatus;
import com.resfeber.pool.core.type.Status;
import com.resfeber.pool.service.PoolService;
import com.resfeber.pool.service.PoolerService;
import com.resfeber.pool.service.bean.PoolBean;
import com.resfeber.pool.service.bean.PoolerBean;
import com.resfeber.pool.service.bean.UserBean;

@Slf4j
@Component
@Path(("/pools"))
public class PoolsController {

    @Autowired
    private PoolService poolService;

    @Autowired
    private PoolWSMapper poolWSMapper;

    @Autowired
    private UserContext userContext;

    @Autowired
    private PoolerService poolerService;

    @Autowired
    private PoolerWSMapper poolerWSMapper;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAllPools() {

        List<PoolBean> poolBeanList = poolService.findByStatus(Status.ACTIVE);
        List<PoolWS> poolWSList = poolBeanList.stream().filter(p -> Objects.nonNull(p)).map(p -> poolWSMapper.fromSource(p)).collect(Collectors.toList());
        return Response.status(200).entity(ResponseWS.builder().code(200).message("SUCCESS").data(poolWSList)).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response schedulePool(PoolWS pool) {
        PoolBean poolBean = poolService.schedulePool(poolWSMapper.toSource(pool));
        return Response.status(201).entity(ResponseWS.builder().code(201).message("CREATED").data(poolWSMapper.fromSource(poolBean))).build();
    }

    @POST
    @Path("/{poolId}/book")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response bookPool(@PathParam("poolId") String poolId) {
        UserBean currentUser = userContext.getCurrentUser().orElseGet(null);
        PoolBean poolBean = poolService.findByUuid(poolId);
        PoolerBean poolerBean = PoolerBean.builder().pool(poolBean).user(currentUser).paymentStatus(PaymentStatus.UNPAID).build();
        poolerBean = poolerService.book(poolerBean);
        return Response.status(201).entity(ResponseWS.builder().code(201).message("CREATED").data(poolerWSMapper.fromSource(poolerBean))).build();
    }


    @PUT
    @Path("/{poolId}/update/{status}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response updatePool(@PathParam("poolId") String poolId, @PathParam("status") Status status) {
        PoolBean poolBean = poolService.findByUuid(poolId);
        poolBean = poolService.updateStatus(poolBean, status);
        return Response.status(202).entity(ResponseWS.builder().code(202).message("ACCEPTED").data(poolWSMapper.fromSource(poolBean))).build();
    }
}
