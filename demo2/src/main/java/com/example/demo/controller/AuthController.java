package com.example.demo.controller;

import com.example.demo.bean.RespVO;
import com.example.demo.utils.RespUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public RespVO login(String username, String password) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return RespUtil.fail(500, "用户名或密码错误");
        }
        Serializable sessionId = subject.getSession().getId();
        return RespUtil.success(sessionId);
    }
}
