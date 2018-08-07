package com.resfeber.pool.service.mapper.user;

import org.springframework.stereotype.Component;

import com.resfeber.pool.core.mapper.Mapper;
import com.resfeber.pool.data.model.User;
import com.resfeber.pool.service.bean.UserBean;

@Component
public class UserMapper implements Mapper<User, UserBean> {
    @Override
    public UserBean fromSource(User user) {
        return UserBean.builder().id(user.getId()).uuid(user.getUuid()).status(user.getStatus().toString()).externalId(user.getExternalId()).email(user.getEmail()).firstName(user.getFirstName()).lastName(user.getLastName()).givenName(user.getGivenName()).profile(user.getProfile()).avatar(user.getAvatar()).gender(user.getGender()).phone(user.getPhone()).build();
    }

    @Override
    public User toSource(UserBean userBean) {
        return User.builder().externalId(userBean.getExternalId()).email(userBean.getEmail()).firstName(userBean.getFirstName()).lastName(userBean.getLastName()).givenName(userBean.getGivenName()).profile(userBean.getProfile()).avatar(userBean.getAvatar()).gender(userBean.getGender()).phone(userBean.getPhone()).build();
    }
}
