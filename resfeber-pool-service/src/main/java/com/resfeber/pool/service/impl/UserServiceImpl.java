package com.resfeber.pool.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resfeber.pool.core.type.Status;
import com.resfeber.pool.data.model.User;
import com.resfeber.pool.data.repository.UserRepository;
import com.resfeber.pool.service.UserService;
import com.resfeber.pool.service.bean.UserBean;
import com.resfeber.pool.service.exception.UserNotFoundException;
import com.resfeber.pool.service.exception.UserServiceException;
import com.resfeber.pool.service.mapper.user.UserMapper;

@Slf4j
@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<UserBean> findByStatus(Status status) {
        try {
            List<User> users = userRepository.findByStatus(status);
            List<UserBean> userBeanList = users.stream().filter(m -> Objects.nonNull(m)).map(u -> userMapper.fromSource(u)).collect(Collectors.toList());
            return userBeanList;
        } catch (Exception ex) {
            log.error("Failed to fetch users for status {} due to {}", status, ex);
            throw new UserServiceException("Failed to fetch users for status " + status.name(), ex);
        }
    }

    @Override
    public UserBean findByEmail(String email) {
        try {
            User user = userRepository.findByEmailAndStatus(email, Status.ACTIVE);
            return Objects.isNull(user) ? null : userMapper.fromSource(user);
        } catch (Exception ex) {
            log.error("Failed to fetch user for email {} due to {}", email, ex);
            throw new UserServiceException("Failed to fetch user for email " + email, ex);
        }
    }

    @Override
    public UserBean findByUuid(String uuid) {
        try {
            User user = userRepository.findByUuid(uuid);
            return Objects.isNull(user) ? null : userMapper.fromSource(user);
        } catch (Exception ex) {
            log.error("Failed to fetch user for uuid {} due to {}", uuid, ex);
            throw new UserServiceException("Failed to fetch user for uuid " + uuid, ex);
        }
    }

    @Transactional
    @Override
    public UserBean saveOrUpdate(UserBean userBean) {
        try {
            User user = userRepository.findByEmailAndStatus(userBean.getEmail(), Status.ACTIVE);
            if (Objects.isNull(user)) {
                user = userMapper.toSource(userBean);
            } else {
                //TODO  copy properties
            }
            user = userRepository.saveAndFlush(user);
            return userMapper.fromSource(user);
        } catch (Exception ex) {
            log.error("Failed to save user {} due to {}", userBean.toString(), ex);
            throw new UserServiceException("Failed to save user  " + userBean.getEmail(), ex);
        }
    }

    @Transactional
    @Override
    public UserBean updatePhone(UserBean userBean) {
        try {
            User user = userRepository.findByUuid(userBean.getUuid());
            if (Objects.isNull(user)) {
                log.error("User not found for uuid {}", userBean.getUuid());
                throw new UserNotFoundException("User not found", null);
            }
            user.setPhone(userBean.getPhone());
            user = userRepository.saveAndFlush(user);
            return userMapper.fromSource(user);
        } catch (Exception ex) {
            log.error("Failed to update phone user {} due to {}", userBean.toString(), ex);
            throw new UserServiceException("Failed to update phone user  " + userBean.toString(), ex);
        }
    }

    @Transactional
    @Override
    public void delete(UserBean userBean) {
        try {
            User user = userRepository.findByEmailAndStatus(userBean.getEmail(), Status.ACTIVE);
            user.setStatus(Status.DELETED);
            userRepository.saveAndFlush(user);
        } catch (Exception ex) {
            log.error("Failed to delete user {} due to {}", userBean.toString(), ex);
            throw new UserServiceException("Failed to delete user  " + userBean.getEmail(), ex);
        }
    }
}
