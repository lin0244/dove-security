package com.licc.dove.security.vo;

import java.util.Date;

import lombok.Data;

/**
 *
 * @author lichangchao
 * @version 1.0.0
 * @date 2018/1/15 15:26
 * @see
 */
@Data
public class DovePlatUserVO {

  private Long    id;

  private Long    userId;

  private String  userName;

  private String  plat;


  private Boolean deleteFlag;


  private Date createTime;


  private Date    updateTime;
}
