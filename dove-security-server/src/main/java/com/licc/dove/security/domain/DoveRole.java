package com.licc.dove.security.domain;

import java.util.Date;

import com.licc.dove.dao.anno.AutoIncrease;
import com.licc.dove.dao.anno.Column;
import com.licc.dove.dao.anno.Id;
import com.licc.dove.dao.anno.Table;

import lombok.Data;

/**
 * 角色表-实体类
 * @author lichangchao
 * @version 1.0.0
 * @date 2018/1/8 17:00
 * @see
 */

@Data
@Table(name = "dove_role")
public class DoveRole {
  @Id
  @AutoIncrease
  private Long id;

  private String name;

  private String code;


  private String plat;

  private String remark;
  //0：系统默认 1：自定义
  private Integer type;
  @Column(name = "version_num")
  private Long versionNum;

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
