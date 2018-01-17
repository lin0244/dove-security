package com.licc.dove.security.domain;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.licc.dove.dao.anno.Column;
import com.licc.dove.dao.anno.Id;
import com.licc.dove.dao.anno.Sequence;
import com.licc.dove.dao.anno.Table;

import lombok.Data;


/**
 * @author lichangchao
 * @Time 2017 -03-20 15:05:56
 */

@Table(name = "dove_user")
@Data
public class User   implements Serializable {

    @Id
    @Sequence()
    private Long id;

    @NotNull
    @Column(name = "user_name")
    private String userName;
    @NotNull
    @Column(name = "plat")
    private String plat;
    @JsonIgnore

    @Column(name = "password")
    private String password;



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
    private Date updateTime;


}
