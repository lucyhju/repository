package com.example.demo.config;

import com.example.demo.shiro.AdminRealm;
import com.example.demo.shiro.CustomSessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;


@Configuration
public class ShiroConfig {

    // 注册自定义的SessionManager
    @Bean
    public CustomSessionManager sessionManager() {
        CustomSessionManager sessionManager = new CustomSessionManager();
        sessionManager.setDeleteInvalidSessions(true); // 删除失效的Session
        sessionManager.setGlobalSessionTimeout(1000 * 60 * 60 * 3); // 全局Session超时时间
        return sessionManager;
    }

    @Bean
    public DefaultWebSecurityManager defaultSecurityManager(AdminRealm adminRealm, CustomSessionManager customSessionManager) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(adminRealm);
        defaultWebSecurityManager.setSessionManager(customSessionManager);
        return defaultWebSecurityManager;
    }


    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //指定安全管理器
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);
        //认证失败跳转url
        shiroFilterFactoryBean.setLoginUrl("/index");

        LinkedHashMap<String, String> filters = new LinkedHashMap<>();
        //登录放行
        filters.put("/auth/login", "anon");
        //其余需要认证
        filters.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filters);
        return shiroFilterFactoryBean;

    }

    //启用声明式鉴权
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager defaultWebSecurityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(defaultWebSecurityManager);
        return authorizationAttributeSourceAdvisor;
    }

}
