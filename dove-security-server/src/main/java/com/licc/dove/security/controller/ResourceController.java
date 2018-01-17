package com.licc.dove.security.controller;

import com.licc.dove.security.param.DoveResourcepParam;
import com.licc.dove.security.service.ResourceService;
import com.licc.dove.security.service.RoleResourceService;
import com.licc.dove.security.util.TreeViewUtil;
import com.licc.dove.security.vo.DoveResourceVO;
import com.licc.dove.security.controller.base.BaseController;
import com.licc.dove.security.util.ResponseVo;
import com.licc.dove.security.util.ResponseVoUtil;
import java.io.IOException;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.licc.dove.dao.Page;

/**
 * 资源
 * @author lichangchao
 * @version 1.0.0
 * @date 2018/1/12 14:13
 * @see
 */
@Controller
@RequestMapping(value = "/security/resource")
public class ResourceController extends BaseController {
  @Resource
  ResourceService resourceService;
  @Resource
  RoleResourceService roleResourceService;
  @RequestMapping(value = "/toListPage", method = RequestMethod.GET)
  public String toListPage() {
    return "/security/resourceList";
  }

  @RequestMapping(value = "/ajaxData", method = RequestMethod.POST)
  @ResponseBody
  public ResponseVo ajaxData(DoveResourcepParam param, HttpServletRequest request) throws IOException {
    Page<DoveResourceVO> pages = resourceService.Page(param,"DoveResourceMapper.findList",
        DoveResourceVO.class);
    return this.result(pages, request.getParameter("draw"));
  }

  @RequestMapping(value = "/list", method = RequestMethod.GET)
  @ResponseBody
  public ResponseVo list( Long roleId) throws IOException {
    List<DoveResourceVO> list = resourceService.listByParentId(0l);
    Map<Long,Long > roleMap =  roleResourceService.getResByRoleId(roleId);
    return ResponseVoUtil.successData(TreeViewUtil.treeResData(list,roleMap));
  }


}
