package com.licc.dove.security.param;

import lombok.Data;

/**
 * 角色和资源关联查询参数
 * 
 * @author lichangchao
 * @version 1.0.0
 * @date 2018/1/9 17:59
 * @see
 */
@Data
public class DoveRoleResourceParam extends PageParam {
    private Long    id;
    private Long    roleId;
    private Long    resourceId;
    private String  plat;
    public String   resName;
    public String   resPath;
    public String   roleName;
    public String   roleCode;
    private Integer type;

}
