package com.licc.dove.security.domain;

import com.licc.dove.dao.anno.AutoIncrease;
import com.licc.dove.dao.anno.Column;
import com.licc.dove.dao.anno.Id;
import com.licc.dove.dao.anno.Table;
import java.util.Date;
import lombok.Data;

/**
 *
 * @author lichangchao
 * @version 1.0.0
 * @date 2018/1/15 14:55
 * @see
 */
@Data
@Table(name = "dove_plat_user")
public class DovePlatUser {
  @Id
  @AutoIncrease
  private Long    id;

  @Column(name = "user_id")
  private Long    userId;
  @Column(name = "user_name")

  private String  userName;

  private String  plat;


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
