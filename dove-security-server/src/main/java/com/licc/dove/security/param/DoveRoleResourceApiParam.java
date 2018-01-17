package com.licc.dove.security.param;

import lombok.Data;

/**
 *  角色和资源关联参数
 * @author lichangchao
 * @version 1.0.0
 * @date 2018/1/9 17:59
 * @see
 */
@Data
public class DoveRoleResourceApiParam {
     public String resName;
     public String resPath;
     public  String plat;
     public String[] roles;
}
