package com.licc.dove.security.vo;

import java.util.Date;

import com.licc.dove.dao.anno.Table;

import java.util.List;
import lombok.Data;

@Data
@Table(name = "dove_resource")
public class DoveResourceVO {

  private Long    id;

  private Long    parentId;

  private String  name;

  private String  path;
  // 是否匿名 0:不匿名访问 1:匿名访问
  private Boolean anonymous;
  // 是否需要授权 0:登录后可访问 1:登陆后需授权才能访问
  private Boolean requireAuthority;
  private String  plat;

  private String  remark;


  private Long    versionNum;

  private Boolean deleteFlag;
  /**
   * 创建时间
   */

  private Date createTime;
  /**
   * 修改时间
   */

  private Date    updateTime;


  private String parentName;


  private String anonymousName;
  private String requireAuthorityName;

  private List<DoveResourceVO> children;

  public String getAnonymousName() {
    if(anonymous){
      return  "是";
    }else {
      return  "否";
    }

  }



  public String getRequireAuthorityName() {
    if(requireAuthority){
      return  "是";
    }else {
      return  "否";
    }
  }

}
