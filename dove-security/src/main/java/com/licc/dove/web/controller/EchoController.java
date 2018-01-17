package com.licc.dove.web.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.AbstractHandlerMethodMapping;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

import com.licc.dove.web.annotation.DefaultHasRoles;
import com.licc.dove.web.annotation.PublicMethod;
import com.licc.dove.web.annotation.RequireAuthority;
import com.licc.dove.web.exception.ConstException;
import com.licc.dove.web.param.DoveResourceParam;
import com.licc.dove.web.param.DoveRoleParam;
import com.licc.dove.web.param.DoveRoleResourceApiParam;
import com.licc.dove.web.param.PageRes;
import com.licc.dove.web.param.PlatUser;
import com.licc.dove.web.param.PlatUserParam;
import com.licc.dove.web.security.DefaultRoleEnum;
import com.licc.dove.web.security.DoveSecurityManager;
import com.licc.dove.web.service.EchoService;
import com.licc.dove.web.service.ServerClient;
import com.licc.dove.web.util.ReflectionUtil;
import com.licc.dove.web.util.ResponseVo;

@RestController
@RequestMapping("/sys/dove/echo/")
public class EchoController {

    @Autowired
    ApplicationContext  applicationContext;
    @Autowired
    ServerClient        serverClient;
    @Autowired
    DoveSecurityManager doveSecurityManager;

    @Autowired
    EchoService         echoService;

    /**
     * 测试连接
     */
    @RequestMapping(value = "testConnection", method = RequestMethod.GET)
    public String testConnection() {
        return "连接成功";
    }

    /**
     * 提供平台用户接口
     */
    @RequestMapping(value = "getPlatUsers", method = RequestMethod.GET)
    public ResponseVo getPlatUsers(PlatUserParam platUserParam) {
        PageRes<PlatUser> platUsers = doveSecurityManager.getPlatUsers(platUserParam);
        return ResponseVo.BUILDER().setCode(ConstException.SUCCESS.getCode()).setData(platUsers);
    }

    /**
     * 初始化资源、角色、平台用户信息
     */
    @RequestMapping(value = "init", method = RequestMethod.GET)
    @ResponseBody

    public String init() {
        try {
            // 初始化默认角色信息
            List<DoveRoleParam> roleParams = ReflectionUtil.getDefaultRoles(DefaultRoleEnum.class);
            for (DoveRoleParam roleParam : roleParams) {
                roleParam.setPlat(doveSecurityManager.getPlat());
            }
            serverClient.initDefaultRoles(roleParams);

            // 初始化资源信息
            List<DoveResourceParam> resources = echoService.getResources(getMapRet());
            serverClient.initResource(resources);

            // 初始化默认角色资源信息
            List<DoveRoleResourceApiParam> roleResourceParams = echoService.getRoleResource(getMapRet());
            serverClient.initDefaultRoleResource(roleResourceParams);
            return "success";

        } catch (Exception e) {
            e.printStackTrace();
            return "fail";

        }
    }

    @RequestMapping("publicUrls")
    public Set<String> getAllPublicUrls() {
        Map<RequestMappingInfo, HandlerMethod> mapRet = getMapRet();
        Set<String> publicUrls = new HashSet<String>();
        for (RequestMappingInfo mapping : mapRet.keySet()) {
            PublicMethod classAno = mapRet.get(mapping).getBeanType().getAnnotation(PublicMethod.class);
            PublicMethod methodAno = mapRet.get(mapping).getMethodAnnotation(PublicMethod.class);
            if (classAno != null || methodAno != null) {
                String url = mapping.getPatternsCondition().getPatterns().iterator().next();
                publicUrls.add(url);
            }
        }
        return publicUrls;
    }

    /**
     * 获取一般登录用户可以访问的url，一般来说所有的url登录后都可以访问，如果一个方法需要显式的授权，需要加上注解RequireAuthority
     * 
     * @return
     */
    @RequestMapping("getDefaultUserUrls")
    public Set<String> getDefaultUserUrls() {

        Map<RequestMappingInfo, HandlerMethod> mapRet = getMapRet();
        Set<String> defaultUserUrls = new HashSet<String>();
        for (RequestMappingInfo mapping : mapRet.keySet()) {
            RequireAuthority classAno = mapRet.get(mapping).getBeanType().getAnnotation(RequireAuthority.class);
            RequireAuthority methodAno = mapRet.get(mapping).getMethodAnnotation(RequireAuthority.class);
            DefaultHasRoles classHasRolesAno = mapRet.get(mapping).getBeanType().getAnnotation(DefaultHasRoles.class);
            DefaultHasRoles methodHasRolesAno = mapRet.get(mapping).getMethodAnnotation(DefaultHasRoles.class);
            if (classAno != null || methodAno != null || classHasRolesAno != null || methodHasRolesAno != null) {
                continue;
            }
            String url = mapping.getPatternsCondition().getPatterns().iterator().next();
            defaultUserUrls.add(url);
        }
        return defaultUserUrls;
    }

    /**
     * 获取所有方法信息
     * 
     * @return
     */
    public Map<RequestMappingInfo, HandlerMethod> getMapRet() {
        AbstractHandlerMethodMapping<RequestMappingInfo> objHandlerMethodMapping = (AbstractHandlerMethodMapping<RequestMappingInfo>) applicationContext
                .getBean("requestMappingHandlerMapping");
        Map<RequestMappingInfo, HandlerMethod> mapRet = objHandlerMethodMapping.getHandlerMethods();
        return mapRet;
    }

}
