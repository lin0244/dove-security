package com.licc.dove.security.service;

import com.licc.dove.dao.Page;
import com.licc.dove.security.param.RoleParam;
import com.licc.dove.security.service.base.impl.BaseServiceImpl;
import com.licc.dove.security.vo.DoveRoleVO;
import com.licc.dove.security.enums.TypeEnum;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.licc.dove.dao.CommonDao;
import com.licc.dove.dao.ParamMap;
import com.licc.dove.security.domain.DoveRole;
import com.licc.dove.security.enums.ECustomUUID;
import com.licc.dove.security.param.DoveRoleParam;

/**
 *
 * @author lichangchao
 * @version 1.0.0
 * @date 2018/1/9 10:09
 * @see
 */
@Service
public class RoleService extends BaseServiceImpl<DoveRole> {
    @Resource
    CommonDao commonDao;

    /**
     * <strong>初始化默认角色信息</strong>
     * <ul>
     * <li>1.新增或修改系统默认的角色，设置版本号</li>
     * <li>2.删除版本号低的默认角色信息</li>
     * </ul>
     * 
     * @param roleParams
     */
    @Transactional
    public void initDefaultRoles(List<DoveRoleParam> roleParams) {
        String plat = null;
        // 1.新增或修改系统默认的角色，设置版本号
        Long version = ECustomUUID.INSTANCE.getInstance().generate();
        for (DoveRoleParam roleParam : roleParams) {
            String code = roleParam.getCode();
            String name = roleParam.getName();

            plat = roleParam.getPlat();
            DoveRole role = getByCodeAndNameAndPlat(code, plat);
            if (role != null) {
                role.setUpdateTime(new Date());
                role.setVersionNum(version);
                role.setName(name);
                role.setType(TypeEnum.SYS.getCode());
                commonDao.update(role);
            } else {
                role =  new DoveRole();

                role.setName(name);
                role.setPlat(plat);
                role.setCode(code);
                role.setVersionNum(version);
                role.setType(TypeEnum.SYS.getCode());
                role.setCreateTime(new Date());
                commonDao.save(role);
            }

        }

        // 2.删除版本号低的默认角色信息
        if (plat != null) {
            deleteByMaxVersionNum(plat);
        }

    }

    DoveRole getByCodeAndNameAndPlat(String code,  String plat) {
        DoveRole doveRole = new DoveRole();

        doveRole.setCode(code);
        doveRole.setPlat(plat);
        doveRole.setDeleteFlag(false);
        List<DoveRole> doveRoleList = commonDao.listByExample(doveRole);
        if (CollectionUtils.isEmpty(doveRoleList))
            return null;
        return doveRoleList.get(0);
    }


    void deleteByMaxVersionNum(String plat) {
        ParamMap paramMap = new ParamMap();
        paramMap.put("plat", plat);
        Map<String, Object> maxVersionMap = commonDao.findOne("DoveRoleMapper.getMaxVersionNum", paramMap);
        Long maxVersion = (Long) maxVersionMap.get("versionNum");
        paramMap.put("maxVersion", maxVersion);
        commonDao.execute("DoveRoleMapper.deleteByMaxVersionNum", paramMap);
    }

  /*****************************************管理后台*******************************************/
    public Page<DoveRoleVO> Page(RoleParam param) {
        Page<DoveRoleVO> menuPage = this.Page(param ,"DoveRoleMapper.findList",DoveRoleVO.class);
        return menuPage;

    }


    public List<DoveRole> list(String plat){
        DoveRole doveRole =  new DoveRole();
        doveRole.setPlat(plat);
        doveRole.setDeleteFlag(false);
       return commonDao.listByExample(doveRole);
    }


}
