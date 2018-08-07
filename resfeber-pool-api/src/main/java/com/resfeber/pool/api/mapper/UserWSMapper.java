package com.resfeber.pool.api.mapper;

import java.util.Objects;

import org.springframework.stereotype.Component;

import com.resfeber.pool.api.ws.UserWS;
import com.resfeber.pool.core.mapper.Mapper;
import com.resfeber.pool.service.bean.UserBean;

@Component
public class UserWSMapper implements Mapper<UserBean, UserWS> {
    @Override
    public UserWS fromSource(UserBean userBean) {
        return Objects.isNull(userBean) ? null : UserWS.builder().uuid(userBean.getUuid()).status(userBean.getStatus()).firstName(userBean.getFirstName()).lastName(userBean.getLastName()).phone(userBean.getPhone()).email(userBean.getEmail()).build();
    }

    @Override
    public UserBean toSource(UserWS userWS) {
        return Objects.isNull(userWS) ? null : UserBean.builder().uuid(userWS.getUuid()).phone(userWS.getPhone()).build();
    }
}
