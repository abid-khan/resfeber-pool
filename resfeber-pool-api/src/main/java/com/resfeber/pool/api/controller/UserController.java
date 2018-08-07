package com.resfeber.pool.api.controller;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
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
import com.resfeber.pool.api.mapper.UserWSMapper;
import com.resfeber.pool.api.util.UserContext;
import com.resfeber.pool.api.ws.PoolWS;
import com.resfeber.pool.api.ws.PoolerWS;
import com.resfeber.pool.api.ws.ResponseWS;
import com.resfeber.pool.api.ws.UserWS;
import com.resfeber.pool.service.PoolService;
import com.resfeber.pool.service.PoolerService;
import com.resfeber.pool.service.UserService;
import com.resfeber.pool.service.bean.PoolBean;
import com.resfeber.pool.service.bean.PoolerBean;
import com.resfeber.pool.service.bean.UserBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Slf4j
@Component
@Path("/users")
@Api(value = "UserController", produces = "application/json")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserWSMapper userWSMapper;
    @Autowired
    private PoolService poolService;
    @Autowired
    private PoolWSMapper poolWSMapper;
    @Autowired
    private PoolerService poolerService;
    @Autowired
    private PoolerWSMapper poolerWSMapper;
    @Autowired
    private UserContext userContext;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response getLoggedInUserDetail() {
        UserBean userBean = userContext.getCurrentUser().orElse(null);
        return Response.status(202).entity(ResponseWS.builder().code(202).message("ACCEPTED").data(userWSMapper.fromSource(userBean)).build()).build();
    }

    @GET
    @Path("{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response findByUuid(@PathParam("userId") String userId) {
        UserBean userBean = userService.findByUuid(userId);
        return Response.status(202).entity(ResponseWS.builder().code(202).message("ACCEPTED").data(userWSMapper.fromSource(userBean)).build()).build();
    }

    @ApiOperation(value = "Update user", response = ResponseWS.class)
    @ApiResponses(value = {
            @ApiResponse(code = 202, message = "ACCEPTED", response = UserWS.class)
    })
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(UserWS userWS) {
        UserBean userBean = userService.updatePhone(userWSMapper.toSource(userWS));
        return Response.status(202).entity(ResponseWS.builder().code(202).message("ACCEPTED").data(userWSMapper.fromSource(userBean))).build();
    }

    @GET
    @Path(("/pools"))
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response findPoolByUser() {
        UserBean userBean = userContext.getCurrentUser().orElse(null);
        List<PoolBean> pools = poolService.findByUserUuid(userBean.getUuid());
        List<PoolWS> poolWSList = pools.stream().filter(p -> Objects.nonNull(p)).map(p -> poolWSMapper.fromSource(p)).collect(Collectors.toList());
        return Response.status(200).entity(ResponseWS.builder().code(200).message("OK").data(poolWSList)).build();
    }

    @GET
    @Path(("/trips"))
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response findTripsByUser() {
        UserBean userBean = userContext.getCurrentUser().orElse(null);
        List<PoolerBean> pools = poolerService.findByUserUuid(userBean.getUuid());
        List<PoolerWS> poolerWSList = pools.stream().filter(p -> Objects.nonNull(p)).map(p -> poolerWSMapper.fromSource(p)).collect(Collectors.toList());
        return Response.status(200).entity(ResponseWS.builder().code(200).message("OK").data(poolerWSList).build()).build();
    }

}
