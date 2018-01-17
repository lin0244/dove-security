package com.licc.dove.web.service;

import com.licc.dove.web.param.DoveResourceParam;
import com.licc.dove.web.annotation.RequireAuthority;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

import com.licc.dove.web.annotation.DefaultHasRoles;
import com.licc.dove.web.annotation.PublicMethod;
import com.licc.dove.web.security.DoveSecurityManager;

/**
 *
 * @author lichangchao
 * @version 1.0.0
 * @date 2018/1/9 10:12
 * @see
 */
@Service
class ResourceService {
    @Autowired
    DoveSecurityManager doveSecurityManager;

    /**
     * 获取controller 资源信息
     * 
     * @param mapRet
     * @param mapping
     * @return
     */
    DoveResourceParam getControllerResource(Map<RequestMappingInfo, HandlerMethod> mapRet, RequestMappingInfo mapping) {
        String plat = doveSecurityManager.getPlat();
        // 获取controller-mapping信息，method的上级信息
        boolean requireAuthority = false;
        boolean anonymous = false;
        RequestMapping requestMappingAno = mapRet.get(mapping).getBeanType().getAnnotation(RequestMapping.class);
        RequireAuthority requireAuthorityAno = mapRet.get(mapping).getBeanType().getAnnotation(RequireAuthority.class);
        PublicMethod publicMethodAno = mapRet.get(mapping).getBeanType().getAnnotation(PublicMethod.class);
        DefaultHasRoles defaultHasRolesAno = mapRet.get(mapping).getBeanType().getAnnotation(DefaultHasRoles.class);

        DoveResourceParam parentResource = null;
        if (requestMappingAno != null) {
            // 判断是否匿名访问
            if (publicMethodAno != null) {
                anonymous = true;
            }
            // 判断登录后是否需要权限访问
            if (requireAuthorityAno != null || defaultHasRolesAno != null) {
                requireAuthority = true;
            }
            String[] paths =  requestMappingAno.value();
            if(paths.length==0) return null;
            String parentPath =paths[0];
            String parentName = requestMappingAno.name();
            parentResource = new DoveResourceParam();
            parentResource.setPath(parentPath);
            parentResource.setName(parentName);
            parentResource.setRequireAuthority(requireAuthority);
            parentResource.setPlat(plat);
            parentResource.setAnonymous(anonymous);
        }
        return parentResource;
    }

    /**
     * 获取 controller-method 资源信息
     * 
     * @param mapRet
     * @param mapping
     * @param controllerResource
     * @return
     */
     DoveResourceParam getMethodResource(Map<RequestMappingInfo, HandlerMethod> mapRet, RequestMappingInfo mapping,
            DoveResourceParam controllerResource) {
        String plat = doveSecurityManager.getPlat();
        String path = mapping.getPatternsCondition().getPatterns().iterator().next();
        String name = mapping.getName();
        RequireAuthority requireAuthorityAno = mapRet.get(mapping).getMethodAnnotation(RequireAuthority.class);
        PublicMethod publicMethodAno = mapRet.get(mapping).getMethodAnnotation(PublicMethod.class);
        DefaultHasRoles defaultHasRolesAno = mapRet.get(mapping).getMethodAnnotation(DefaultHasRoles.class);
        boolean requireAuthority = false;
        boolean anonymous = false;
        // 判断是否匿名访问
        if (publicMethodAno != null) {
            anonymous = true;
        }
        // 判断登录后是否需要权限访问
        if (requireAuthorityAno != null || defaultHasRolesAno != null) {
            requireAuthority = true;
        }

        DoveResourceParam resource = new DoveResourceParam();
        resource.setPath(path);
        resource.setName(name);
        resource.setParentRes(controllerResource);
        resource.setPlat(plat);
        resource.setRequireAuthority(requireAuthority);
        resource.setAnonymous(anonymous);
        return resource;
    }

}
