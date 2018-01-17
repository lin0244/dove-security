package com.licc.dove.security.controller;

import com.licc.dove.security.controller.base.BaseController;
import com.licc.dove.security.domain.DovePlat;
import com.licc.dove.security.service.PlatService;
import com.licc.dove.security.util.ResponseVo;
import com.licc.dove.security.util.ResponseVoUtil;
import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 平台信息
 * 
 * @author lichangchao
 * @version 1.0.0
 * @date 2018/1/16 13:40
 * @see
 */
@RequestMapping(value = "plat")
@Controller
public class PlatController extends BaseController {
    @Resource
    PlatService platService;
    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVo update(DovePlat plat) {
        if (plat.getId() == null) {
            return ResponseVoUtil.failResult("ID不能空");
        }
        platService.update(plat);
        return ResponseVoUtil.successMsg("修改成功");
    }
}
