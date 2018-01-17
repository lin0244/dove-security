package com.licc.dove.security.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.licc.dove.dao.CommonDao;
import com.licc.dove.security.domain.DoveUserRole;

/**
 *
 * @author lichangchao
 * @version 1.0.0
 * @date 2018/1/16 17:05
 * @see
 */
@Service
public class UserRoleService {

    @Resource
    CommonDao commonDao;

    public Map<Long, Long> getByUserId(Long userId) {
        DoveUserRole userRole = new DoveUserRole();
        userRole.setUserId(userId);
        userRole.setDeleteFlag(false);
        List<DoveUserRole> list = commonDao.listByExample(userRole);
        if (CollectionUtils.isEmpty(list))
            return null;
        Map<Long, Long> map = new HashMap<>();
        for (DoveUserRole role : list) {
            map.put(role.getRoleId(), userId);
        }
        return map;
    }

    public void save(Long userId, String roles, String plat) {
        // 删除原有的角色信息
        deleteByUserId(userId, plat);
        if (!StringUtils.isEmpty(roles)) {
            String[] roless = roles.split(",");
            for (String role : roless) {
                DoveUserRole userRole = new DoveUserRole();
                userRole.setDeleteFlag(false);
                userRole.setUserId(userId);
                userRole.setRoleId(Long.parseLong(role));
                userRole.setPlat(plat);
                userRole.setCreateTime(new Date());
                commonDao.save(userRole);
            }
        }

    }

    public void deleteByUserId(Long userId, String plat) {
        DoveUserRole userRole = new DoveUserRole();
        userRole.setDeleteFlag(false);
        userRole.setUserId(userId);
        userRole.setPlat(plat);
        List<DoveUserRole> list = commonDao.listByExample(userRole);
        if (!CollectionUtils.isEmpty(list)) {
            for (DoveUserRole doveUserRole : list) {
                doveUserRole.setDeleteFlag(true);
                doveUserRole.setUpdateTime(new Date());
                commonDao.update(doveUserRole);
            }
        }

    }
}
