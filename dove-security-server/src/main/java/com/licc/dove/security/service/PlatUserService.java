package com.licc.dove.security.service;

import com.licc.dove.security.service.base.impl.BaseServiceImpl;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.licc.dove.security.domain.DovePlatUser;
import com.licc.dove.security.param.PlatUser;

/**
 * 平台用户信息
 * 
 * @author lichangchao
 * @version 1.0.0
 * @date 2018/1/15 14:58
 * @see
 */

@Service
public class PlatUserService extends BaseServiceImpl<DovePlatUser> {
    /********************************************** 提供API **********************************************/
    /**
     * 初始化平台用户信息
     * 
     * @param platUserList
     */
    public void initPlatUser(List<PlatUser> platUserList) {
        for (PlatUser platUser : platUserList) {
            String userName = platUser.getName();
            String plat = platUser.getPlat();
            Long userId = platUser.getId();
            DovePlatUser dovePlatUser = this.getByUserIdAndUserName(userId, plat);
            if (dovePlatUser != null) {
                dovePlatUser.setUserName(userName);
                dovePlatUser.setUpdateTime(new Date());
                this.commonDao.update(dovePlatUser);
            } else {
                dovePlatUser = new DovePlatUser();
                dovePlatUser.setUserName(userName);
                dovePlatUser.setPlat(plat);
                dovePlatUser.setUserId(userId);
                dovePlatUser.setCreateTime(new Date());
                this.commonDao.save(dovePlatUser);

            }

        }

    }

    public DovePlatUser getByUserIdAndUserName(Long userId, String plat) {
        DovePlatUser platUser = new DovePlatUser();
        platUser.setPlat(plat);
        platUser.setUserId(userId);
        platUser.setDeleteFlag(false);
        List<DovePlatUser> list = commonDao.listByExample(platUser);
        if (CollectionUtils.isNotEmpty(list)) {
            return list.get(0);
        } else {
            return null;
        }
    }
    /************************************* 管理后台 ***********************************************/

}
