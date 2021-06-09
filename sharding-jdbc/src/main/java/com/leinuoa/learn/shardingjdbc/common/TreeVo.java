package com.leinuoa.learn.shardingjdbc.common;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Collections;
import java.util.List;

@NoArgsConstructor
@Data
public class TreeVo<T extends TreeVo<T>> implements Comparable<TreeVo<T>>{
    protected String id;
    protected String parentId;
    private String name;
    /**每一层单独排序*/
    protected Integer sort;
    protected List<T> children;

    public TreeVo(String rootId){
        this.id = rootId;
    }

    public TreeVo(String id, String parentId, String name, Integer sort){
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.sort = sort;
    }

    public void setChildren(List<T> children) {
        Collections.sort(children);
        this.children = children;
    }

    @Override
    public int compareTo(TreeVo o) {
        return sort.compareTo(o.getSort());
    }
}
