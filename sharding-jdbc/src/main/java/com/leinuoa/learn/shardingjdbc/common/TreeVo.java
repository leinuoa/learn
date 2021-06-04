package com.leinuoa.learn.shardingjdbc.common;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
public class TreeVo<T> {
    protected String id;
    protected String parentId;
    private String name;
    /**每一层单独排序*/
    protected Integer sort;
    protected List<T> children = new ArrayList<>();

    public TreeVo(String rootId){
        this.id = rootId;
    }

    public TreeVo(String id, String parentId, String name){
        this.id = id;
        this.parentId = parentId;
        this.name = name;
    }
}
