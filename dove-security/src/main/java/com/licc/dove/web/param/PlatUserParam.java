package com.licc.dove.web.param;

import lombok.Data;

/**
 *
 * @author lichangchao
 * @version 1.0.0
 * @date 2018/1/16 13:04
 * @see
 */
@Data
public class PlatUserParam {
  /**分页参数***/
  private Integer page = 0;
  private Integer size = 10;
  /**用户名称***/
  private String userName;
}
