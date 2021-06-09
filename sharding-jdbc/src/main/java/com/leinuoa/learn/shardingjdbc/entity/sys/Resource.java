package com.leinuoa.learn.shardingjdbc.entity.sys;

import com.alibaba.fastjson.JSON;
import com.leinuoa.learn.shardingjdbc.common.TreeUtil;
import com.leinuoa.learn.shardingjdbc.common.TreeVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Arrays;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class Resource extends TreeVo<Resource> {
    private String name;
    private String classifyCode;
    public Resource(String id, String parentId, String name, Integer sort, String classifyCode){
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.sort = sort;
        this.classifyCode = classifyCode;
    }
    public static void main(String[] args) {
        Resource r1 = new Resource("0",null,"四川省",1,"001");
        Resource r2 = new Resource("1","0","成都市",1,"001001");
        Resource r3 = new Resource("2","0","巴中市",2,"001002");
        Resource r4 = new Resource("3","1","锦江区",5,"001001001");
        Resource r5 = new Resource("4","2","恩阳区",3,"001002002");
        Resource r6 = new Resource("5","2","巴州区",4,"001002001");
        Resource r7 = new Resource("6","1","高新区",7,"001001002");
        Resource r8 = new Resource("7","1","新津区",6,"001001003");
        List<Resource> list = Arrays.asList(r1,r2,r3,r4,r5,r6,r7,r8);
        TreeUtil<Resource> tree = new TreeUtil<>();
        System.out.println(JSON.toJSONString(tree.toTree(list)));
    }
}
