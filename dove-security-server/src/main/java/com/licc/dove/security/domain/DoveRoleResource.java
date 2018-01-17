package com.licc.dove.security.domain;

import java.util.Date;

import com.licc.dove.dao.anno.AutoIncrease;
import com.licc.dove.dao.anno.Column;
import com.licc.dove.dao.anno.Id;
import com.licc.dove.dao.anno.Table;

import lombok.Data;

/**
 * 角色资源表-实体类
 * @author lichangchao
 * @version 1.0.0
 * @date 2018/1/8 17:00
 * @see
 */

@Data
@Table(name = "dove_role_resource")
public class DoveRoleResource {
  @Id
  @AutoIncrease
  private Long id;

  @Column(name = "role_id")
  private Long roleId;


  @Column(name = "resource_id")
  private Long resourceId;

  //0：系统内置 1：自定义
  private Integer type;



  private String plat;


  @Column(name = "delete_flag")
  private Boolean deleteFlag;
  /**
   * 创建时间
   */
  @Column(name = "create_time")

  private Date createTime;
  /**
   * 修改时间
   */
  @Column(name = "update_time")

  private Date updateTime;
}
