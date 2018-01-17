package com.licc.dove.security.param;



import com.licc.dove.dao.anno.Table;
import lombok.Data;

@Data
@Table(name = "dove_resource")
public class DoveResourcepParam  extends  PageParam{

  private Long    id;

  private Long    parentId;

  private String  name;

  private String  path;
  // 是否匿名 0:不匿名访问 1:匿名访问
  private Boolean anonymous;
  // 是否需要授权 0:登录后可访问 1:登陆后需授权才能访问
  private Boolean requireAuthority;

  private String  remark;


  private Long    versionNum;

}
