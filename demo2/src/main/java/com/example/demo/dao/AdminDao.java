package com.example.demo.dao;

import com.example.demo.bean.Admin;

import java.util.List;

public interface AdminDao {
    Admin queryAdminByName(String username);

    List<Integer> queryRoleIdByUsername(String username);

    List<String> queryPermissionByRoleId(Integer id);
}
