package com.licc.dove.web.service;

import com.licc.dove.web.param.DoveResourceParam;
import com.licc.dove.web.param.DoveRoleResourceApiParam;
import com.licc.dove.web.annotation.DefaultHasRoles;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

import com.licc.dove.web.security.DoveSecurityManager;

/**
 *
 * @author lichangchao
 * @version 1.0.0
 * @date 2018/1/8 17:47
 * @see
 */
@Service
public class EchoService {

    @Resource
    ResourceService     resourceService;
    @Autowired
    DoveSecurityManager doveSecurityManager;
    @Resource
    ServerClient serverClient;
    /**
     * 获取资源信息
     * 
     * @param mapRet
     */
    public List<DoveResourceParam> getResources(Map<RequestMappingInfo, HandlerMethod> mapRet) {
        List<DoveResourceParam> list = new ArrayList<>();
        try {
            for (RequestMappingInfo mapping : mapRet.keySet()) {
                DoveResourceParam parentRes = resourceService.getControllerResource(mapRet, mapping);
                DoveResourceParam resource = resourceService.getMethodResource(mapRet, mapping, parentRes);
                list.add(resource);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return Collections.EMPTY_LIST;
        }
        return list;
    }

    /**
     * 获取角色资源关联信息
     */

    public List<DoveRoleResourceApiParam> getRoleResource(Map<RequestMappingInfo, HandlerMethod> mapRet) {
        String plat = doveSecurityManager.getPlat();
        List<DoveRoleResourceApiParam> resources = new ArrayList<>();
        for (RequestMappingInfo mapping : mapRet.keySet()) {
            String path = mapping.getPatternsCondition().getPatterns().iterator().next();
            String name = mapping.getName();
            DefaultHasRoles methodAno = mapRet.get(mapping).getMethodAnnotation(DefaultHasRoles.class);
            if (methodAno == null)
                continue;
            String[] roles = methodAno.roles();
            DoveRoleResourceApiParam doveRoleResource = new DoveRoleResourceApiParam();
            doveRoleResource.setPlat(plat);
            doveRoleResource.setRoles(roles);
            doveRoleResource.setResName(name);
            doveRoleResource.setResPath(path);
            resources.add(doveRoleResource);
        }

        return resources;

    }



}
