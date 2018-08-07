package com.resfeber.pool.api.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.resfeber.pool.api.config.UIProperties;

@Slf4j
@Controller
public class SinginHandlerController {
    @Autowired
    private UIProperties uiProperties;

    @RequestMapping(value = "/signin/success", method = RequestMethod.GET)
    @ResponseBody
    public void loginSuccess(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("Logged in successfully");
        response.sendRedirect(uiProperties.getUrl() + uiProperties.getDashboard());
    }

    @RequestMapping(value = "/logout/success", method = RequestMethod.GET)
    @ResponseBody
    public void logoutSuccess(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("Logged out successfully");
        response.sendRedirect(uiProperties.getUrl());
    }


}
