package com.licc.dove.web.security;

import com.licc.dove.web.controller.EchoController;
import com.licc.dove.web.service.ServerClient;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

@Component
public class RbacService {

    Set<String>         publicUrls = new HashSet<>();

    @Autowired
    EchoController echoController;
    @Autowired
    ServerClient serverClient;

    @Resource
    DoveSecurityManager doveSecurityManager;

    public RbacService() {
    }

    @PostConstruct
    public void getAllPublicUrls() {

        serverClient.register(doveSecurityManager.getPlat());

        publicUrls.addAll(echoController.getAllPublicUrls());
    }

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        String requestUrl = request.getRequestURI();
        if (authentication instanceof AnonymousAuthenticationToken) {
            return matchUrl(request, publicUrls);
        } else {
            // 注意这里不能用equal来判断，因为有些URL是有参数的，所以要用AntPathMatcher来比较
            List<GrantedMethod> authorites = (List<GrantedMethod>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
            return matchUrl(requestUrl, authorites);
        }
    }

    private boolean matchUrl(String requestUrl, List<GrantedMethod> authorites) {
        if (authorites == null || authorites.isEmpty()) {
            return false;
        }
        boolean hasPermission = false;
        for (GrantedMethod method : authorites) {
            if (antPathMatcher.match(method.getAuthority(), requestUrl)) {
                hasPermission = true;
                break;
            }
        }
        return hasPermission;
    }

    private boolean matchUrl(HttpServletRequest request, Set<String> urls) {
        if (urls == null || urls.isEmpty()) {
            return false;
        }
        boolean hasPermission = false;
        for (String url : urls) {
            if (antPathMatcher.match(url, request.getRequestURI())) {
                hasPermission = true;
                break;
            }
        }
        return hasPermission;
    }
}
