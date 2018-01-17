package com.licc.dove.web.util;

import com.licc.dove.web.param.DoveRoleParam;
import com.licc.dove.web.security.DefaultRoleEnum;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author lichangchao
 * @version 1.0.0
 * @date 2018/1/8 15:46
 * @see
 */
public class ReflectionUtil {
    @Autowired

    public static List<DoveRoleParam> getDefaultRoles(Class clz) throws Exception {
        Reflections reflections = new Reflections("com.jumore", new SubTypesScanner(true));
        Set classs = reflections.getSubTypesOf(DefaultRoleEnum.class);
        if (classs.size() == 0)
            return null;
        Iterator<DefaultRoleEnum> iterator = classs.iterator();
        Object object = iterator.next();
        return getEnumValues((Class) object);
    }

    private static List<DoveRoleParam> getEnumValues(Class clz) throws Exception {
        List<DoveRoleParam> enums = new ArrayList<>();
        if (clz.isEnum()) {
            Object[] objs = clz.getEnumConstants();
            for (Object obj : objs) {
                DefaultRoleEnum defaultRoleEnum = (DefaultRoleEnum) obj;
                DoveRoleParam role = new DoveRoleParam();
                role.setCode(defaultRoleEnum.getCode());
                role.setName(defaultRoleEnum.getName());
                enums.add(role);
            }
        }
        return enums;
    }
}
