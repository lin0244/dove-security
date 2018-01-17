package com.licc.dove.web.security;

import com.licc.dove.web.enums.EResultCode;
import com.licc.dove.web.util.ResponseVo;
import com.licc.dove.web.controller.EchoController;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.licc.dove.web.service.ServerClient;
import com.licc.dove.web.util.Reflect;

@Component
public class DoveAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private DoveSecurityManager doveSecurityManager;

    @Autowired
    EchoController echoController;

    @Autowired
    ServerClient                serverClient;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // if(authentication.isAuthenticated()){
        // return authentication;
        // }
        String username = authentication.getName();// 这个获取表单输入中返回的用户名;
        String password = (String) authentication.getCredentials();// 这个是表单中输入的密码
        DoveWebAuthenticationDetails details = (DoveWebAuthenticationDetails) authentication.getDetails();
        Object user = doveSecurityManager.getAuthenticationHandler().doAuthentication(username, password, details.getParams());
        // 登录验证，获取权限
        // 判断用户名密码
        // UserDetails user =
        // User.withUsername(userName).password(password).roles("dev").build();
        List<GrantedMethod> authorities = doveSecurityManager.getAuthenticationHandler().doAuthorization(user);
        // 获取默认一般登录用户可以访问的url
        Set<String> defaultUserUrls = echoController.getDefaultUserUrls();

        for (String url : defaultUserUrls) {
            GrantedMethod method = new GrantedMethod(url);
            authorities.add(method);

        }

        //通过用户ID 和平台信息获取该用户对应资源信息
        Long userId = Reflect.on(user).field("id").get();
        ResponseVo roleRes = serverClient.getRoleRes(userId, doveSecurityManager.getPlat());
        if (roleRes.getCode() == EResultCode.SUCCESS.getKey()) {
            List<String> resources = (List<String>) roleRes.getData();
            for (String url : resources) {
                GrantedMethod method = new GrantedMethod(url);
                authorities.add(method);
            }
        }
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password, authorities);
        return token;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }

}
