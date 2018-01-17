package com.licc.dove.security.util;

import com.licc.dove.security.domain.DoveRole;
import com.licc.dove.security.vo.DoveResourceVO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

/**
 *
 * @author lichangchao
 * @version 1.0.0
 * @date 2018/1/15 11:02
 * @see
 */
public class TreeViewUtil {

    public static String treeResData(List<DoveResourceVO> doveResourceVOS,Map<Long, Long> resMap) {
        StringBuilder treeData = new StringBuilder("{");
        int length = doveResourceVOS.size();
        for (int i = 0; i < length; i++) {
            DoveResourceVO resourceVO = doveResourceVOS.get(i);
            String name = resourceVO.getName();
            List<DoveResourceVO> childs = resourceVO.getChildren();
            treeData.append("\"" + name + "\"").append(": {").append("\"name\" :").append("\"" + name + "\"").append(",");

            if (CollectionUtils.isEmpty(childs)) {
                treeData .append("\"type\" :").append("\"item\"");
                treeData.append("}");
            } else {
                treeData .append("\"type\" :").append("\"folder\"");

                treeData.append(", \"additionalParameters\":{ \"children\" : {");
                int childLength = childs.size();

                for (int j = 0; j < childLength; j++) {
                    DoveResourceVO child = childs.get(j);
                    String childName = child.getName();
                    Long childId = child.getId();
                    treeData.append("\"" + childName + "\"").append(": {").append("\"name\" :").append("\"" + childName + "\"").append(",")
                            .append("\"id\" :").append("\"" + childId + "\"").append(",").append("\"type\" :").append("\"item\"");
                    if (resMap != null&&resMap.get(childId)!=null){
                        treeData.append(", \"additionalParameters\":{ \"item-selected\":true}}");

                    }else{
                        treeData.append("}");
                    }

                    if (j < childLength - 1) {
                        treeData.append(",");
                    }

                }

                treeData.append("}}}");
            }

            if (i < length - 1) {
                treeData.append(",");
            }

        }
        treeData.append("}");
        return treeData.toString();
    }

    public static String treeRoleData(List<DoveRole> doveRoles, Map<Long, Long> roleMap) {
        StringBuilder treeData = new StringBuilder("{");
        int length = doveRoles.size();
        for (int i = 0; i < length; i++) {
            DoveRole role = doveRoles.get(i);
            String name = role.getName();
            treeData.append("\"" + name + "\"").append(": {").append("\"name\" :").append("\"" + name + "\"").append(",").append("\"id\" :")
                    .append("\"" + role.getId() + "\"").append(",\"type\" : \"item\"");
              if (roleMap != null&&roleMap.get(role.getId())!=null){
                treeData.append(", \"additionalParameters\":{ \"item-selected\":true}}");

              }else{
                treeData.append("}");
              }
              if (i < length - 1) {
                  treeData.append(",");
              }

        }
        treeData.append("}");
        return treeData.toString();
    }

//    public static void main(String arge[]) {
//        List<DoveResourceVO> list = new ArrayList<DoveResourceVO>();
//        DoveResourceVO doveResourceVO1 = new DoveResourceVO();
//        doveResourceVO1.setName("aaaa");
//        list.add(doveResourceVO1);
//        List<DoveResourceVO> listChild = new ArrayList<DoveResourceVO>();
//        DoveResourceVO cdoveResourceVO1 = new DoveResourceVO();
//        cdoveResourceVO1.setName("aaaa1");
//        cdoveResourceVO1.setId(1l);
//        listChild.add(cdoveResourceVO1);
//        DoveResourceVO cdoveResourceVO11 = new DoveResourceVO();
//        cdoveResourceVO11.setName("aaaa2");
//        cdoveResourceVO11.setId(1l);
//        listChild.add(doveResourceVO1);
//        doveResourceVO1.setChildren(listChild);
//
//        DoveResourceVO doveResourceVO2 = new DoveResourceVO();
//        doveResourceVO2.setName("bbbb");
//        list.add(doveResourceVO2);
//        DoveResourceVO doveResourceVO3 = new DoveResourceVO();
//        doveResourceVO3.setName("cccc");
//        list.add(doveResourceVO3);
//        Map<Long,Long> map = new HashMap<>();
//        map.put(1l,123l);
//        System.out.println(TreeViewUtil.treeResData(list,map));
//        List<DoveRole> roles = new ArrayList<>();
//        DoveRole r1 = new DoveRole();
//        r1.setName("1111");
//        r1.setId(1l);
//        roles.add(r1);
//        roles.add(r1);
//        roles.add(r1);
//
//      System.out.println(TreeViewUtil.treeRoleData(roles,map));
//
//    }

}
