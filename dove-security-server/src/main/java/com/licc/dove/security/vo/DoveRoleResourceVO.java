package com.licc.dove.security.vo;

import com.licc.dove.security.enums.TypeEnum;
import lombok.Data;

/**
 *
 * @author lichangchao
 * @version 1.0.0
 * @date 2018/1/15 13:40
 * @see
 */
@Data
public class DoveRoleResourceVO {
   private  Long id;
   private String  roleName;
   private String roleCode;
   private String resName;
   private String resPath;
   private Integer type;
   private  String plat;

  private String  typeName;

  public String getTypeName() {

    if (type == TypeEnum.SYS.getCode().intValue()) {
      return "系统内置";
    } else {
      return "用户自定义";
    }

  }
}
