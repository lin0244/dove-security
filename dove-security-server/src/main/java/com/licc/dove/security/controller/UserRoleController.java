package com.licc.dove.security.controller;

import com.licc.dove.security.controller.base.BaseController;
import com.licc.dove.security.service.UserRoleService;
import com.licc.dove.security.util.ResponseVo;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.licc.dove.security.util.ResponseVoUtil;

/**
 *
 * @author lichangchao
 * @version 1.0.0
 * @date 2018/1/16 18:26
 * @see
 */
@Controller
@RequestMapping(value = "/security/userRole")
public class UserRoleController extends BaseController {
    @Resource
    UserRoleService userRoleService;

    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVo save(HttpServletRequest request, @RequestParam(required = true) Long userId, String roles) {
        userRoleService.save(userId, roles, getSessionUser(request).getPlat());
        return ResponseVoUtil.successMsg("保存成功");
    }

}
