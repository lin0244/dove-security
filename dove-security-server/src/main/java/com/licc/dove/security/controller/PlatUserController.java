package com.licc.dove.security.controller;

import com.licc.dove.security.util.ResponseVo;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.licc.dove.dao.Page;
import com.licc.dove.security.controller.base.BaseController;
import com.licc.dove.security.param.PlatUser;
import com.licc.dove.security.param.PlatUserParam;
import com.licc.dove.security.service.PlatUserService;
import com.licc.dove.security.util.HttpClientUtil;

/**
 * 平台用户管理
 * 
 * @author lichangchao
 * @version 1.0.0
 * @date 2018/1/15 14:26
 * @see
 */
@Controller
@RequestMapping(value = "/security/platUser")
public class PlatUserController extends BaseController {
    static final String URL = "/sys/dove/echo/getPlatUsers";
    @Resource
    PlatUserService     platUserService;

    @RequestMapping(value = "/toListPage", method = RequestMethod.GET)
    public String toListPage() {
        return "/security/platUserList";
    }

    @RequestMapping(value = "/ajaxData", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVo ajaxData(PlatUserParam param, HttpServletRequest request) throws IOException {
        String platUrl = getDovePlat(request).getUrl();
        Map<String,String> map = new HashMap<>();
        map.put("userName",param.getUserName());
        map.put("size",param.getSize().toString());
        map.put("page",param.getPage().toString());
        String res = HttpClientUtil.get(platUrl + URL, map);
        ResponseVo responseVo = new ObjectMapper().readValue(res, ResponseVo.class);
        Page<PlatUser>  pages = null;
        if(responseVo.getCode().intValue() == 0){
           pages = getPage((Map<String, Object>) responseVo.getData());
        }
        return this.result(pages, request.getParameter("draw"));
    }

    private Page<PlatUser> getPage(Map<String,Object> map){
        Page<PlatUser> platUserPage = new Page<>();
        platUserPage.setPageSize((Integer) map.get("pageSize"));
        platUserPage.setCurrentPage((Integer) map.get("currentPage"));
        platUserPage.setCurrentResult((Integer) map.get("currentResult"));
        platUserPage.setTotalResult((Integer) map.get("totalResult"));
        platUserPage.result = (List<PlatUser>) map.get("result");

        return platUserPage;
    }
}
