package com.licc.dove.security.service;

import com.licc.dove.security.service.base.impl.BaseServiceImpl;
import com.licc.dove.security.vo.DoveResourceVO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.licc.dove.dao.CommonDao;
import com.licc.dove.dao.ParamMap;
import com.licc.dove.security.domain.DoveResource;
import com.licc.dove.security.enums.ECustomUUID;
import com.licc.dove.security.param.DoveResourceParam;
import com.licc.dove.security.util.BeanMapper;

/**
 *
 * @author lichangchao
 * @version 1.0.0
 * @date 2018/1/10 13:54
 * @see
 */
@Service
public class ResourceService extends BaseServiceImpl<DoveResource> {
    @Resource
    CommonDao commonDao;

    /**
     * <strong>初始化资源信息</strong>
     * <ul>
     * <li>1.初始化method的父级资源contorller</li>
     * <li>2.初始化method资源信息</li>
     * <li>3.删除版本号低的资源信息</li>
     * </ul>
     * 
     * @param params
     */

    public void initResource(List<DoveResourceParam> params) {
        String plat = null;
        Long version = ECustomUUID.INSTANCE.getInstance().generate();
        for (DoveResourceParam resourceParam : params) {
            plat = resourceParam.getPlat();
            DoveResourceParam parentResParam = resourceParam.getParentRes();
            DoveResource parentRes = null;
            String path = parentResParam.getPath();
            if (!path.contains("/sys/dove/echo/") || !path.equals("${server.error.path:${error.path:/error}}") || !path.equals("error")) {
                // 处理上级资源（controller ）
                if (parentResParam != null) {
                    // parentResParam.setRequireAuthority(true);
                    parentRes = save(parentResParam, version, 0L);
                }
                // 处理method资源信息
                save(resourceParam, version, parentRes == null ? null : parentRes.getId());
            }
        }
        if (plat != null) {
            deleteByMaxVersionNum(plat);
        }
    }

    private DoveResource save(DoveResourceParam resourceParam, Long version, Long parentId) {
        String plat = resourceParam.getPlat();
        String name = resourceParam.getName();
        String path = resourceParam.getPath();
        if (StringUtils.isEmpty(name)) {
            name = path;
        }
        DoveResource resource = getByPathAndNameAndPlat(path, plat);

        if (resource != null) {
            resource.setAnonymous(resourceParam.getAnonymous());
            resource.setRequireAuthority(resourceParam.getRequireAuthority());
            resource.setVersionNum(version);
            resource.setName(name);
            resource.setUpdateTime(new Date());
            resource.setParentId(parentId);
            commonDao.update(resource);
        } else {
            resource = BeanMapper.map(resourceParam, DoveResource.class);
            resource.setVersionNum(version);
            resource.setCreateTime(new Date());
            resource.setParentId(parentId);
            resource.setName(name);
            commonDao.save(resource);
        }

        return resource;

    }

    DoveResource getByPathAndNameAndPlat(String path, String plat) {
        DoveResource resource = new DoveResource();
        resource.setPath(path);
        resource.setPlat(plat);
        resource.setDeleteFlag(false);
        List<DoveResource> doveRoleList = commonDao.listByExample(resource);
        if (CollectionUtils.isEmpty(doveRoleList))
            return null;
        return doveRoleList.get(0);
    }

    private void deleteByMaxVersionNum(String plat) {

        ParamMap paramMap = new ParamMap();
        paramMap.put("plat", plat);
        Map<String, Object> maxVersionMap = commonDao.findOne("DoveResourceMapper.getMaxVersionNum", paramMap);
        Long maxVersion = (Long) maxVersionMap.get("versionNum");
        paramMap.put("maxVersion", maxVersion);
        commonDao.execute("DoveResourceMapper.deleteByMaxVersionNum", paramMap);
    }

    /************************************************ 管理后台 **********************************/

    public List<DoveResourceVO> listByParentId(Long parentId) {
        ParamMap paramMap = new ParamMap();
        paramMap.put("plat", getPlat());
        paramMap.put("parentId", parentId);
        List<DoveResourceVO> resourceVOS = commonDao.listByParams(DoveResourceVO.class, "DoveResourceMapper.findList", paramMap);
        if (CollectionUtils.isEmpty(resourceVOS))
            return Collections.EMPTY_LIST;
        List<DoveResourceVO> list = new ArrayList<>();

        for (DoveResourceVO doveResourceVO : resourceVOS) {
            Long id = doveResourceVO.getId();
            paramMap.put("plat", getPlat());
            paramMap.put("parentId", id);
            paramMap.put("requireAuthority", true);
            List<DoveResourceVO> children = commonDao.listByParams(DoveResourceVO.class, "DoveResourceMapper.findList", paramMap);
            if (!CollectionUtils.isEmpty(children)) {
                doveResourceVO.setChildren(children);
                list.add(doveResourceVO);
            }

        }

        return list;
    }

}
