package com.licc.dove.security.service;

import com.licc.dove.security.domain.DovePlat;
import com.licc.dove.security.param.UserParam;
import com.licc.dove.security.service.base.impl.BaseServiceImpl;
import com.licc.dove.security.util.BeanMapper;
import com.licc.dove.security.util.ResponseVo;
import com.licc.dove.security.util.ResponseVoUtil;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.licc.dove.dao.CommonDao;
import com.licc.dove.security.domain.User;

/**
 * @author lichangchao
 * @Time 2017 -03-29 09:21:05
 */
@Service
@Transactional
public class UserService extends BaseServiceImpl<User> {
    @Resource
    CommonDao commonDao;

    public User register(UserParam userParam) {
        User user = BeanMapper.map(userParam, User.class);
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setCreateTime(new Date());
        User user1 = getByUserName(user.getUserName());
        if (user1 == null) {
            commonDao.save(user);
        }
        String plat = userParam.getPlat();
        DovePlat dovePlat =  getByPlat(plat);
        if(dovePlat==null){
            dovePlat = new DovePlat();
            dovePlat.setDeleteFlag(false);
            dovePlat.setPlat(plat);
            dovePlat.setCreateTime(new Date());
            commonDao.save(dovePlat);
        }

        return user;
    }

    public ResponseVo update(UserParam param) {
        User user = new User();
        user.setPlat(param.getPlat());
        user.setUserName(param.getUserName());
        user.setDeleteFlag(false);
        List<User> list = commonDao.listByExample(user);
        if (CollectionUtils.isEmpty(list)) {
            return ResponseVoUtil.failResult("没有该用户");
        } else {

            user = list.get(0);
            boolean flag = new BCryptPasswordEncoder().matches(param.getOldPassword(), user.getPassword());
            if (!flag) {
                return ResponseVoUtil.failResult("老密码错误");

            }
            user.setPassword(new BCryptPasswordEncoder().encode(param.getNewPassword()));
            commonDao.update(user);
        }
        return ResponseVoUtil.successMsg("修改成功");
    }

    public User getByUserName(String userName) {
        User user = new User();
        user.setUserName(userName);
        user.setDeleteFlag(false);
        List<User> list = commonDao.listByExample(user);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }


    public DovePlat getByPlat(String plat){
        DovePlat dovePlat =  new DovePlat();
        dovePlat.setPlat(plat);
        dovePlat.setDeleteFlag(false);
        List<DovePlat> list = commonDao.listByExample(dovePlat);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }
}
