package com.licc.dove.security.controller;

import com.licc.dove.security.param.PlatUser;
import com.licc.dove.security.param.UserParam;
import com.licc.dove.security.service.PlatUserService;
import com.licc.dove.security.service.UserService;
import com.licc.dove.security.service.RoleService;
import com.licc.dove.security.util.ResponseVo;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.licc.dove.security.param.DoveResourceParam;
import com.licc.dove.security.param.DoveRoleParam;
import com.licc.dove.security.param.DoveRoleResourceApiParam;
import com.licc.dove.security.service.ResourceService;
import com.licc.dove.security.service.RoleResourceService;
import com.licc.dove.security.util.ResponseVoUtil;

/**
 * 对客户端提供接口
 * 
 * @author lichangchao
 * @version 1.0.0
 * @date 2018/1/9 17:27
 * @see
 */
@RestController
@RequestMapping(value = "api")
public class ApiController {
  @Resource
  RoleService roleService;
  @Resource
  ResourceService resourceService;
  @Resource
  RoleResourceService roleResourceService;

  @Resource
  PlatUserService platUserService;

  @Resource
  UserService userService;
  @RequestMapping(value = "test",method = RequestMethod.GET)
  public String test(){
    return "asdfsdf";
  }


    /**
    *   初始化角色信息
    */
   @RequestMapping(value = "initDefaultRoles",method = RequestMethod.POST)
   @HystrixCommand(fallbackMethod="fallbackMethod")

   public ResponseVo initDefaultRoles(@RequestBody List<DoveRoleParam> roleParams){
     roleService.initDefaultRoles(roleParams);
     return ResponseVoUtil.successMsg("初始化角色信息成功");
   }
  /**
   *   初始化资源信息
   */
  @RequestMapping(value = "initResource",method = RequestMethod.POST)
  public ResponseVo initResource(@RequestBody List<DoveResourceParam> params){
    resourceService.initResource(params);
    return ResponseVoUtil.successMsg("初始化资源信息成功");
  }


  /**
   *   初始化角色信息
   */
  @RequestMapping(value = "initDefaultRoleResource",method = RequestMethod.POST)
  public ResponseVo initDefaultRoleResource(@RequestBody List<DoveRoleResourceApiParam> params){
    roleResourceService.initDefaultRoleResource(params);
    return ResponseVoUtil.successMsg("初始化默认角色资源信息成功");
  }



  /**
   *   初始化平台用户信息
   */
  @RequestMapping(value = "initPlatUser",method = RequestMethod.POST)
  public ResponseVo initPlatUser(@RequestBody List<PlatUser> params){
    platUserService.initPlatUser(params);
    return ResponseVoUtil.successMsg("初始化平台用户信息成功");
  }
  /**
   * 通过 用户ID和平台编码获取该用户对应角色信息
   * @param userId
   * @param plat
   * @return

   */
  @RequestMapping(value = "/getRoleRes", method = RequestMethod.POST)
  public ResponseVo getRoleRes(@RequestParam(name = "userId") Long userId,@RequestParam(name = "plat") String plat) {
    List<String> list = roleResourceService.getRoleRes(userId,plat);
    return ResponseVoUtil.successData(list);
  }

  /**
   * 通过平台编码注册用户 默认用户名为平台编码 密码为111111
   * @param plat
   * @return

   */
  @RequestMapping(value = "/register", method = RequestMethod.POST)
  public ResponseVo register(@RequestParam(name = "plat") String plat) {
    UserParam userParam = new UserParam();
    userParam.setUserName(plat);
    userParam.setPlat(plat);
    userParam.setPassword("111111");
    userService.register(userParam);


    return ResponseVoUtil.successMsg("平台用户注册成功");
  }

  public ResponseVo fallbackMethod(List<DoveRoleParam> roleParams){

    return ResponseVoUtil.failResult("平台编码:调用服务端接口失败");
  }
}
