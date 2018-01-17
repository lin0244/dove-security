package com.licc.dove.security.service.base.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.licc.dove.dao.CommonDao;
import com.licc.dove.dao.Page;
import com.licc.dove.dao.ParamMap;
import com.licc.dove.security.param.BaseParam;
import com.licc.dove.security.param.PageParam;
import com.licc.dove.security.service.base.IBaseService;
import com.licc.dove.security.util.BeanMapper;
import com.licc.dove.security.util.GenericsUtils;
import com.licc.dove.security.util.Reflect;
import com.licc.dove.security.util.SessionUtil;

public class BaseServiceImpl<T> implements IBaseService<T> {
    private transient Class<T> DTOClass = GenericsUtils.getSuperClassGenricType(this.getClass(), 0);

    @Autowired
    public CommonDao                  commonDao;

    @Override
    public void deleteById(Long id) {
        T t = commonDao.get(DTOClass, id);
        commonDao.delete(t);
    }

    @Override
    public <P extends BaseParam> void save(P param) {
        if (param.getId() == null) {
            param.setCreateTime(new Date());
        } else {
            param.setUpdateTime(new Date());
        }
        param.setDeleteFlag(false);
        T t = BeanMapper.map(param, DTOClass);
        if (param.getId() == null) {
            commonDao.save(t);
        } else {
            commonDao.update(t);
        }

    }

    @Override
    public void updateDeleteFlagById(Long id) {
        T t = commonDao.get(DTOClass, id);
        Reflect.on(t).set("deleteFlag", true);
        commonDao.update(t);
    }

    @Override
    public T findOne(Long id) {
        return commonDao.get(DTOClass, id);
    }

    @Override
    public <V, P extends PageParam> Page<V> Page(P param, String statement, Class clz) {
        ParamMap paramMap = new ParamMap(param);
        paramMap.put("plat",getPlat());
        Page<V> page = commonDao.findPageByParams(clz, transPage(param), statement, paramMap);
        return page;
    }

    /****************************** 公共方法 ************************/

     public  Page transPage(PageParam pageParam) {
        Page page = new Page();
        page.setCurrentPage(pageParam.getPage());
        page.setPageSize(pageParam.getSize());
        return page;
    }

    public String getPlat() {
        return (String) SessionUtil.getSession().getAttribute("plat");
    }

}
