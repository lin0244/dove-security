package com.licc.dove.security.controller.base;

import com.licc.dove.security.domain.DovePlat;
import com.licc.dove.security.service.MenuService;
import com.licc.dove.security.util.ResponseVo;
import com.licc.dove.security.vo.MenuVO;
import java.security.Principal;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.licc.dove.dao.Page;
import com.licc.dove.security.domain.User;
import com.licc.dove.security.service.UserService;
import com.licc.dove.security.util.ResponseVoUtil;

/**
 *
 * @author lichangchao
 * @version 1.0.0
 * @date 2017/12/25 17:36
 * @see
 */

public class BaseController {
    private static final String User_Session = "USER_SESSION";
    @Resource
    MenuService menuService;
    @Autowired
    protected UserService       userService;

    @ModelAttribute
    public void init(Map<String, Object> model, Principal principal, HttpServletRequest request) {
        List<MenuVO> menuList = menuService.findAndChildrenByParentId(false, 0L);
        String name = principal.getName();
        User user = (User) request.getSession().getAttribute(User_Session);
        if (user == null) {
            user = userService.getByUserName(name);
            request.getSession().setAttribute(User_Session, user);
            request.getSession().setAttribute("plat", user.getPlat());
        }

        model.put("userName", name);
        model.put("menus", menuList);

    }

    public User getSessionUser(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(User_Session);
        return user;

    }

    public DovePlat getDovePlat(HttpServletRequest request) {
        DovePlat dovePlat = userService.getByPlat(getSessionUser(request).getPlat());
        return dovePlat;
    }

    public <T> ResponseVo result(Page<T> page, String draw) {
        if (StringUtils.isEmpty(draw))
            draw = "0";
        return ResponseVoUtil.successData(page).setDraw(Integer.parseInt(draw));
    }
}
