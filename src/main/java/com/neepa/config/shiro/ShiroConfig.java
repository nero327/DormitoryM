package com.neepa.config.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class ShiroConfig {
    //三大对象具有强关联性

    //ShiroFilterFactoryBean:3
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("SecurityManager") DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        //关联安全管理器
        bean.setSecurityManager(defaultWebSecurityManager);
        /**
         * anon: 匿名过滤器，未登陆也可以访问
         * authc: 认证过滤器， 登陆后访问
         * perms : 需要xx权限，才能访问
         * roles: 需要xx角色，才能访问
         * user: 需要xx用户，才能访问
         * port:指定端口才能访问
         * ssl:必须使用https协议才能访问
         * logout :登出功能
         * rest :根据指定HTTP请求访问才能访问 ，get方式提交 或者 post方式提交才能访问
         */
        Map<String, String> filterMap = new HashMap<>();
        filterMap.put("/login","anon");
        bean.setFilterChainDefinitionMap(filterMap);

        //设置登录请求跳转
        bean.setLoginUrl("/admin/login");

        //设置未授权请求跳转
        bean.setUnauthorizedUrl("/noauth");
        return bean;
    }

    /**
     * 系统自带的Realm管理，主要针对多realm 认证
     */
    @Bean
    public UserModularRealmAuthorizer modularRealmAuthorizer(){
        UserModularRealmAuthorizer modularRealmAuthorizer = new UserModularRealmAuthorizer();
        return modularRealmAuthorizer;
    }
    @Bean
    public ModularRealmAuthenticator modularRealmAuthenticator() {
        UserModularRealmAuthenticator modularRealmAuthenticator = new UserModularRealmAuthenticator();
        modularRealmAuthenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
        return modularRealmAuthenticator;
    }

    //DefaultWebSecurityManager:2
    @Bean(name="SecurityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("MySessionManager")DefaultWebSessionManager sessionManager){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        List<Realm> realms = new ArrayList<>();
        realms.add(staffRealm());
        realms.add(studentRealm());
        securityManager.setAuthenticator(modularRealmAuthenticator());
        securityManager.setAuthorizer(modularRealmAuthorizer());
        securityManager.setRealms(realms);

        securityManager.setSessionManager(sessionManager);
        return securityManager;
    }

    //定义Realm对象:1
    @Bean
    public StaffRealm staffRealm(){
        return new StaffRealm();
    }
    @Bean
    public StudentRealm studentRealm(){
        return new StudentRealm();
    }

    //整合shiro thymeleaf
    @Bean
    public ShiroDialect getShiroDialect(){
        return new ShiroDialect();
    }

    @Bean("MySessionManager")
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        // 去掉shiro登录时url里的JSESSIONID
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        return sessionManager;
    }
}
