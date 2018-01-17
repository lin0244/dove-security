package com.licc.dove.security.domain;

import java.util.Date;

import com.licc.dove.dao.anno.AutoIncrease;
import com.licc.dove.dao.anno.Column;
import com.licc.dove.dao.anno.Id;
import com.licc.dove.dao.anno.Table;

import lombok.Data;

/**
 * 资源表-实体类
 * @author lichangchao
 * @version 1.0.0
 * @date 2018/1/8 17:00
 * @see
 */

@Data
@Table(name = "dove_resource")
public class DoveResource {
    @Id
    @AutoIncrease
    private Long    id;

    @Column(name = "parent_id")
    private Long    parentId;

    private String  name;

    private String  path;
    // 是否匿名 0:不匿名访问 1:匿名访问
    private Boolean anonymous;
    // 是否需要授权 0:登录后可访问 1:登陆后需授权才能访问
    @Column(name = "require_authority")
    private Boolean requireAuthority;
    private String  plat;

    private String  remark;


    @Column(name = "version_num")
    private Long    versionNum;

    @Column(name = "delete_flag")
    private Boolean deleteFlag;
    /**
     * 创建时间
     */
    @Column(name = "create_time")

    private Date    createTime;
    /**
     * 修改时间
     */
    @Column(name = "update_time")

    private Date    updateTime;
}
