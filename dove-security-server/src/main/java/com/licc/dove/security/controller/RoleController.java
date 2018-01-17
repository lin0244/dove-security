package com.licc.dove.security.controller;

import com.licc.dove.security.controller.base.BaseController;
import com.licc.dove.security.domain.DoveRole;
import com.licc.dove.security.enums.TypeEnum;
import com.licc.dove.security.service.RoleService;
import com.licc.dove.security.service.UserRoleService;
import com.licc.dove.security.util.ResponseVo;
import com.licc.dove.security.util.ResponseVoUtil;
import com.licc.dove.security.util.TreeViewUtil;
import com.licc.dove.security.vo.DoveRoleVO;
import java.io.IOException;
import java.util.List;

import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.licc.dove.dao.Page;
import com.licc.dove.security.param.RoleParam;

/**
 * 角色
 * 
 * @author lichangchao
 * @version 1.0.0
 * @date 2018/1/12 14:13
 * @see
 */
@Controller
@RequestMapping(value = "/security/role")
public class RoleController extends BaseController {
    @Resource
    RoleService roleService;
    @Resource
    UserRoleService userRoleService;

    @RequestMapping(value = "/toListPage", method = RequestMethod.GET)
    public String toListPage() {
        return "/security/roleList";
    }

    @RequestMapping(value = "/ajaxData", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVo ajaxData(RoleParam param, HttpServletRequest request) throws IOException {
        Page<DoveRoleVO> pages = roleService.Page(param);
        return this.result(pages, request.getParameter("draw"));
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVo save(@Validated RoleParam param) throws IOException {
        param.setType(TypeEnum.CUSTOM.getCode());
        roleService.save(param);
        return ResponseVoUtil.successMsg("操作成功");
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseVo delete(@PathVariable("id") Long id) throws IOException {
        roleService.deleteById(id);
        return ResponseVoUtil.successMsg("操作成功");
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public ResponseVo list(HttpServletRequest request, Long userId) throws IOException {

        List<DoveRole> list = roleService.list(getSessionUser(request).getPlat());
        Map<Long,Long > roleMap =  userRoleService.getByUserId(userId);
        return ResponseVoUtil.successData(TreeViewUtil.treeRoleData(list,roleMap));
    }
}
