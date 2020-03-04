package com.example.demo.controller;

import com.example.demo.bean.Admin;
import com.example.demo.bean.RespVO;
import com.example.demo.dao.AdminDao;
import com.example.demo.utils.RespUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminDao adminDao;

    @RequestMapping(value = "{name}", method = RequestMethod.GET)
    @RequiresPermissions("admin:query")
    public RespVO adminInfo(@PathVariable String name) {
        Admin admin = adminDao.queryAdminByName(name);
        return RespUtil.success(admin);
    }
}
