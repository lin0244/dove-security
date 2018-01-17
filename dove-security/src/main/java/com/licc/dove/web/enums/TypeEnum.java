package com.licc.dove.web.enums;

/**
 * Function:(这里用一句话描述这个类的作用)
 *
 * @author Administrator
 * @version 1.0.0
 * @date 2018/1/8 17:52
 * @see
 */
public enum TypeEnum {
  SYS(0),CUSTOM(1);

   Integer code ;
   TypeEnum(int code){
    this.code = code;
  }

  public Integer getCode() {
    return code;
  }

}
