package com.resfeber.pool.service;

import java.util.List;

import com.resfeber.pool.core.type.Status;
import com.resfeber.pool.service.bean.UserBean;

public interface UserService {

    List<UserBean> findByStatus(Status status);

    UserBean findByEmail(String email);

    UserBean findByUuid(String uuid);

    UserBean saveOrUpdate(UserBean userBean);

    UserBean updatePhone(UserBean userBean);

    void delete(UserBean userBean);
}
