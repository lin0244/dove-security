package com.licc.dove.security.controller;

import com.licc.dove.security.param.DoveRoleResourceParam;
import com.licc.dove.security.service.RoleResourceService;
import com.licc.dove.security.vo.DoveRoleResourceVO;
import com.licc.dove.security.util.ResponseVo;
import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.licc.dove.dao.Page;
import com.licc.dove.security.controller.base.BaseController;
import com.licc.dove.security.util.ResponseVoUtil;

/**
 * 角色资源管理
 * @author lichangchao
 * @version 1.0.0
 * @date 2018/1/12 14:13
 * @see
 */
@Controller
@RequestMapping(value = "/security/roleResource")
public class RoleResourceController extends BaseController {
  @Resource
  RoleResourceService roleResourceService;

  @RequestMapping(value = "/toListPage", method = RequestMethod.GET)
  public String toListPage() {
    return "/security/roleResourceList";
  }

  @RequestMapping(value = "/ajaxData", method = RequestMethod.POST)
  @ResponseBody
  public ResponseVo ajaxData(DoveRoleResourceParam param, HttpServletRequest request) throws IOException {
    Page<DoveRoleResourceVO> pages = roleResourceService.Page(param,"DoveRoleResourceMapper.findList",DoveRoleResourceVO.class);
    return this.result(pages, request.getParameter("draw"));
  }

  @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
  @ResponseBody
  public ResponseVo delete(@PathVariable("id") Long id) throws IOException {
    roleResourceService.deleteById(id);
    return ResponseVoUtil.successMsg("操作成功");
  }
  @RequestMapping(value = "/save", method = RequestMethod.POST)
  @ResponseBody
  public ResponseVo save(@RequestParam(required = true) Long roleId,@RequestParam String resIds) throws IOException {
    roleResourceService.save(roleId,resIds);
    return ResponseVoUtil.successMsg("保存成功");
  }

}
