package com.licc.dove.security.vo;

import com.licc.dove.security.enums.TypeEnum;
import java.util.Date;

import com.licc.dove.dao.anno.Table;

import lombok.Data;

@Table(name = "dove_role")
@Data
public class DoveRoleVO {
    private Long    id;

    private String  name;

    private String  code;

    private String  plat;

    private String  remark;
    // 0：系统默认 1：自定义
    private Integer type;
    private Long    versionNum;

    private Boolean deleteFlag;
    /**
     * 创建时间
     */

    private Date    createTime;
    /**
     * 修改时间
     */

    private Date    updateTime;

    private String  typeName;

    public String getTypeName() {

        if (type == TypeEnum.SYS.getCode().intValue()) {
            return "系统默认";
        } else {
            return "用户自定义";
        }

    }

}
