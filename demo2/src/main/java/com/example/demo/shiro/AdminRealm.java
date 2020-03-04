package com.example.demo.shiro;

import com.example.demo.bean.Admin;
import com.example.demo.dao.AdminDao;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class AdminRealm extends AuthorizingRealm {

    @Autowired
    AdminDao adminDao;

    /**
     * 认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        Admin admin = adminDao.queryAdminByName(username);
        if (admin == null) return null;
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(username, admin.getPassword(), this.getName());
        return simpleAuthenticationInfo;
    }

    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = (String) principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        //获取用户所属角色id
        List<Integer> roleId = adminDao.queryRoleIdByUsername(username);

        Set<String> permSet = new HashSet<>();
        //获取用户权限
        for (Integer id : roleId) {
            List<String> permissionList = adminDao.queryPermissionByRoleId(id);
            for (String perm : permissionList) {
                permSet.add(perm);
            }
        }
        simpleAuthorizationInfo.addStringPermissions(permSet);
        return simpleAuthorizationInfo;
    }


}
