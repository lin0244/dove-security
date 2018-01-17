package com.licc.dove.security.enums;


import com.licc.dove.security.util.CustomUUID;

/**
 * 生成全局唯一码 采用单列模式
 * @author lichangchao
 * @Time 2017 -04-13 20:44:26
 */
public enum ECustomUUID {
     INSTANCE;
     private CustomUUID customUUID;
     ECustomUUID(){
            customUUID = new CustomUUID(12);
    }
    public CustomUUID getInstance() {
            return customUUID;
    }
}
