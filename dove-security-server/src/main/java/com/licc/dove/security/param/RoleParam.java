package com.licc.dove.security.param;

import lombok.Data;

@Data
public class RoleParam extends PageParam {
  private String name;
  private String code;
  private Integer type;
  private String remark;
  private String plat;
}
