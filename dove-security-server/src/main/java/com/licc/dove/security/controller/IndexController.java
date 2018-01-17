package com.licc.dove.security.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.licc.dove.security.controller.base.BaseController;
import com.licc.dove.security.domain.DovePlat;
import com.licc.dove.security.util.HttpClientUtil;

/**
 *
 * @author lichangchao
 * @version 1.0.0
 * @date 2017/12/25 13:54
 * @see
 */
@Controller
public class IndexController extends BaseController {

    @RequestMapping("/index")
    public String welcome(Map<String, Object> model, HttpServletRequest request) {
        DovePlat dovePlat = userService.getByPlat(getSessionUser(request).getPlat());
        model.put("url", dovePlat.getUrl());
        model.put("id", dovePlat.getId());
        model.put("plat", dovePlat.getPlat());
        return "index1";
    }

    @RequestMapping(value = "/initPlatRes", method = RequestMethod.GET)
    @ResponseBody
    public String initPlatRes(String url) {
        try {
            String msg = HttpClientUtil.get(url);
            if ("success".equals(msg)) {
                return "初始化成功";
            } else {
                return "初始化失败";
            }
        } catch (Exception e) {
            return "初始化失败";
        }

    }

    @RequestMapping(value = "/testConnection", method = RequestMethod.POST)
    @ResponseBody
    public String welcome(String url) {
        try {
            String msg = HttpClientUtil.get(url);
            return msg;
        } catch (Exception e) {
            return "连接失败";
        }

    }

}
