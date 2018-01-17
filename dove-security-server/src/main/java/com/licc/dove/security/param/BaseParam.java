package com.licc.dove.security.param;

import java.util.Date;

import lombok.Data;

/**
 *
 *
 * @author lichangchao
 * @version 1.0.0
 * @date 2017/10/11 18:14
 * @see
 */
@Data
public class BaseParam {
    private Long id;
    private String plat;
    private Date createTime;
    private Date updateTime;
    private Boolean deleteFlag;
    public void setCreateTime(Date createTime) {
        if (this.id == null) {
            this.createTime = new Date();
        }

    }

    public void setUpdateTime(Date updateTime) {
        if (this.id != null) {
            this.updateTime = new Date();
        }
    }
}
