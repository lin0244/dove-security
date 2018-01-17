package com.licc.dove.security.controller;

import com.licc.dove.security.controller.base.BaseController;
import com.licc.dove.security.param.UserParam;
import com.licc.dove.security.service.UserService;
import com.licc.dove.security.util.ResponseVo;
import com.licc.dove.security.util.ResponseVoUtil;
import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("user")
public class UserController extends BaseController {

    @RequestMapping(value = "toPage" ,method = RequestMethod.GET)
    public String toPage() {
        return "/user/user";
    }

    @Resource
    UserService userService;

    /**
     * <strong>注册用户</strong>
     * 
     * @param userParam
     * 
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVo register(@RequestBody UserParam userParam) {
        userService.register(userParam);
        return ResponseVoUtil.successMsg("注册成功");
    }



    /**
     * <strong>修改密码</strong>
     *
     * @param userParam
     *
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVo update(UserParam userParam) {


        return userService.update(userParam);
    }


    @RequestMapping(value = "/user/delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseVo deleteById(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseVoUtil.successMsg("用户成功");
    }





}
