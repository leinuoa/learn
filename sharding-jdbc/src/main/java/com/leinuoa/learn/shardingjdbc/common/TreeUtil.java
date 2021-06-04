package com.leinuoa.learn.shardingjdbc.common;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TreeUtil<T extends TreeVo> {
    // 作为根节点的parentId值
    private static final String ROOT_VALUE = "0";

    /**
     * 在不知道rootId的情况下，可能有多个rootId的情况下，列表转树
     *
     * @param list
     * @return
     */
    public synchronized List<T> toTree(List<T> list) {
        List<T> treeList = new ArrayList<>();
        if (!list.isEmpty()) {
            List<T> rootList = getRootNode(list);
            rootList.forEach(node -> {
                node = generationTree(list, node);
                treeList.add(node);
            });
        }
        return treeList;
    }

    /**
     * 递归生成树
     *
     * @param list
     * @param node
     */
    public synchronized T generationTree(List<T> list, T node) {
        List<T> child = new ArrayList<>();
        list.forEach(l -> {
            if (l.getParentId().equals(node.getId())) {
                child.add(generationTree(list, l));
            }
        });
        node.setChildren(child);
        return node;
    }

    /**
     * 获取根节点数据
     *
     * @param list
     * @return
     */
    public List<T> getRootNode(List<T> list) {
        return list.stream().filter(t -> t.getParentId().equals(ROOT_VALUE)).collect(Collectors.toList());
    }


    public static void main(String[] args) {
        TreeVo tree1 = new TreeVo("c1", "0", "四川省", 1);
        TreeVo tree2 = new TreeVo("g2", "c1", "成都市", 2);
        TreeVo tree3 = new TreeVo("w3", "c1", "巴中市", 15);
        TreeVo tree4 = new TreeVo("t4", "c1", "绵阳市", 4);
        TreeVo tree5 = new TreeVo("a5", "c1", "德阳市", 5);
        TreeVo tree6 = new TreeVo("s6", "g2", "锦江区", 6);
        TreeVo tree7 = new TreeVo("t7", "g2", "成华区", 7);
        TreeVo tree8 = new TreeVo("8o", "g2", "高新区", 8);
        TreeVo tree9 = new TreeVo("j9", "g2", "新津区", 9);
        TreeVo tree10 = new TreeVo("10", "0", "陕西省", 10);
        TreeVo tree11 = new TreeVo("11", "10", "西安市", 11);
        TreeVo tree12 = new TreeVo("12", "10", "咸阳市", 12);
        TreeVo tree13 = new TreeVo("13", "11", "雁塔区", 13);
        TreeVo tree14 = new TreeVo("14", "11", "高新区", 14);

        List<TreeVo> list = new ArrayList<>();
        list.addAll(Arrays.asList(tree1, tree3, tree5, tree6, tree7, tree8, tree9, tree4, tree10, tree11, tree12, tree13, tree14, tree2));
        TreeUtil<TreeVo> treeUtil = new TreeUtil<>();
        List<TreeVo> tree = treeUtil.toTree(list);
        System.out.println(JSON.toJSONString(tree));
    }

}
