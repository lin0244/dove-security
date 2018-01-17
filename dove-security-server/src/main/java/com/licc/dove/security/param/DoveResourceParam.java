package com.licc.dove.security.param;

import lombok.Data;

/**
 * 客户端-资源参数
 * 
 * @author lichangchao
 * @version 1.0.0
 * @date 2018/1/8 17:00
 * @see
 */

@Data

public class DoveResourceParam {
    private Long id;
    private String  name;

    private String  path;
    // 是否匿名 0:不匿名访问 1:匿名访问
    private Boolean anonymous;

    private Boolean requireAuthority;
    private String  plat;

    private DoveResourceParam parentRes;
}
