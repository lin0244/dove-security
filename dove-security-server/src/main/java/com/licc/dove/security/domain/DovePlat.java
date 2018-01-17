package com.licc.dove.security.domain;

import java.util.Date;

import com.licc.dove.dao.anno.AutoIncrease;
import com.licc.dove.dao.anno.Column;
import com.licc.dove.dao.anno.Id;
import com.licc.dove.dao.anno.Table;

import lombok.Data;

/**
 * 平台表
 * @author lichangchao
 * @version 1.0.0
 * @date 2018/1/15 14:55
 * @see
 */
@Data
@Table(name = "dove_plat")
public class DovePlat {
  @Id
  @AutoIncrease
  private Long    id;

  private String  plat;

  private  String url;

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

  private Date    updateTime;
}
