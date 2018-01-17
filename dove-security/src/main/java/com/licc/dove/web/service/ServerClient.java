package com.licc.dove.web.service;

/**
 * 权限服务接口
 * 
 * @author lichangchao
 * @version 1.0.0
 * @date 2018/1/10 9:50
 * @see
 */

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import com.licc.dove.web.param.DoveRoleParam;
import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.licc.dove.web.param.DoveResourceParam;
import com.licc.dove.web.param.DoveRoleResourceApiParam;
import com.licc.dove.web.util.ResponseVo;

@FeignClient("security-service")
public interface ServerClient {

    /**
     * 初始化默认角色信息
     * @param roleParams
     * @return
     */
    @RequestMapping(method = POST, value = "/api/initDefaultRoles")
    ResponseVo initDefaultRoles(@RequestBody List<DoveRoleParam> roleParams);

    /**
     * 初始化资源信息
     * @param params
     * @return
     */
    @RequestMapping(method = POST, value = "/api/initResource")
    ResponseVo initResource(@RequestBody List<DoveResourceParam> params);

    /**
     * 初始化默认角色对应资源信息
     * @param params
     * @return
     */
    @RequestMapping(method = POST, value = "/api/initDefaultRoleResource")
    ResponseVo initDefaultRoleResource(@RequestBody List<DoveRoleResourceApiParam> params);

//    /**
//     * 初始化平台用户信息
//     * @deprecated
//     * @param params
//     * @return
//     */
//    @RequestMapping(method = POST, value = "/api/initPlatUser")
//    ResponseVo initPlatUser(@RequestBody List<PlatUser> params);

    /**
     * 通过用户ID获取角色对应资源信息
     * @param userId
     * @param plat
     * @return
     */
    @RequestMapping(method = POST, value = "/api/getRoleRes")
    ResponseVo getRoleRes(@RequestParam(name = "userId") Long userId, @RequestParam(name = "plat") String plat);



    /**
     * 通过平台编码注册用户 默认用户名为平台编码 密码为111111
     * @param plat
     * @return
     */
    @RequestMapping(method = POST, value = "/api/register")

    ResponseVo register(@RequestParam(name = "plat") String plat);




}
