package com.licc.dove.security.service;

import com.licc.dove.security.domain.DoveResource;
import com.licc.dove.security.domain.DoveRole;
import com.licc.dove.security.enums.TypeEnum;
import com.licc.dove.security.param.DoveRoleResourceApiParam;
import com.licc.dove.security.service.base.impl.BaseServiceImpl;
import com.licc.dove.security.vo.DoveRoleResourceVO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.licc.dove.dao.CommonDao;
import com.licc.dove.dao.ParamMap;
import com.licc.dove.security.domain.DoveRoleResource;

/**
 *
 * @author lichangchao
 * @version 1.0.0
 * @date 2018/1/10 17:40
 * @see
 */
@Service
public class RoleResourceService extends BaseServiceImpl<DoveRoleResource> {
    @Resource
    CommonDao       commonDao;
    @Resource
    ResourceService resourceService;

    /**
     * 初始化默认角色资源信息
     * 
     * @param params
     */
    public void initDefaultRoleResource(List<DoveRoleResourceApiParam> params) {
        if (!CollectionUtils.isEmpty(params)) {
            String plat = params.get(0).getPlat();
            ParamMap paramMap = new ParamMap();
            paramMap.put("type", TypeEnum.SYS.getCode());
            paramMap.put("plat", plat);
            // 删除默认角色与资源关联关系
            commonDao.execute("DoveRoleResourceMapper.deleteByType", paramMap);
            for (DoveRoleResourceApiParam param : params) {
                String path = param.getResPath();
                DoveResource doveResource = resourceService.getByPathAndNameAndPlat(path, plat);
                if (doveResource == null)
                    continue;
                String[] roles = param.getRoles();
                if (roles.length > 0) {
                    for (String role : roles) {
                        DoveRole doveRole = getByCodeAndPlat(role, plat);
                        if (doveRole == null)
                            continue;
                        DoveRoleResource roleResource = new DoveRoleResource();
                        roleResource.setCreateTime(new Date());
                        roleResource.setDeleteFlag(false);
                        roleResource.setPlat(plat);
                        roleResource.setResourceId(doveResource.getId());
                        roleResource.setRoleId(doveRole.getId());
                        commonDao.save(roleResource);

                    }

                }

            }

        }

    }

    public List<String> getRoleRes(Long userId, String plat) {
        List<String> list = new ArrayList<>();
        ParamMap paramMap = new ParamMap();
        paramMap.put("userId", userId);
        paramMap.put("plat", plat);
        List<DoveRoleResourceVO> doveResourceParams = commonDao.listByParams(DoveRoleResourceVO.class,
                "DoveRoleResourceMapper.listResourceByUserId", paramMap);
        if (CollectionUtils.isEmpty(doveResourceParams))
            return Collections.EMPTY_LIST;
        for (DoveRoleResourceVO param : doveResourceParams) {
            list.add(param.getResPath());
        }
        return list;
    }

    private DoveRole getByCodeAndPlat(String code, String plat) {
        DoveRole doveRole = new DoveRole();
        doveRole.setCode(code);
        doveRole.setPlat(plat);
        doveRole.setDeleteFlag(false);
        List<DoveRole> doveRoleList = commonDao.listByExample(doveRole);
        if (CollectionUtils.isEmpty(doveRoleList))
            return null;
        return doveRoleList.get(0);
    }

    public Map<Long, Long> getResByRoleId(Long roleId) {
        Map<Long, Long> map = new HashMap<>();
        DoveRoleResource roleResource = new DoveRoleResource();
        roleResource.setRoleId(roleId);
        roleResource.setPlat(getPlat());
        roleResource.setDeleteFlag(false);
        List<DoveRoleResource> list = commonDao.listByExample(roleResource);
        if (CollectionUtils.isEmpty(list))
            return null;
        for (DoveRoleResource roleResource1 : list) {
            map.put(roleResource1.getResourceId(), roleId);
        }
        return map;
    }

    /***************************** 管理后台 ****************************/

    public void save(Long roleId, String resIds) {
        delete(roleId);
        if (!StringUtils.isEmpty(resIds)) {
            String[] ress = resIds.split(",");
            for (String res : ress) {
                DoveRoleResource roleResource = new DoveRoleResource();
                roleResource.setDeleteFlag(false);
                roleResource.setRoleId(roleId);
                roleResource.setPlat(getPlat());
                roleResource.setResourceId(Long.parseLong(res));
                roleResource.setCreateTime(new Date());
                commonDao.save(roleResource);

            }
        }
    }

    public void delete(Long roleId) {
        DoveRoleResource roleResource = new DoveRoleResource();
        roleResource.setDeleteFlag(false);
        roleResource.setPlat(getPlat());
        roleResource.setRoleId(roleId);
        List<DoveRoleResource> list = commonDao.listByExample(roleResource);
        if (!CollectionUtils.isEmpty(list)) {
            for (DoveRoleResource roleResource1 : list) {
                roleResource1.setDeleteFlag(true);
                roleResource1.setUpdateTime(new Date());
                commonDao.update(roleResource1);
            }
        }

    }
}
