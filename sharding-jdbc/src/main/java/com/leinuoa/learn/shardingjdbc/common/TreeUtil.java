package com.leinuoa.learn.shardingjdbc.common;

import com.alibaba.fastjson.JSON;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class TreeUtil<T extends TreeVo<T>> {
    // 根节点parentId的值
    private static final String ROOT_VALUE = null;

    /**
     * 列表转树，支持多树
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
            if (node.getId().equals(l.getParentId())) {
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
        return list.stream().filter(t -> t.getParentId() == null).collect(Collectors.toList());
    }

    public static void main(String[] args) {

//        String strPool = "0123456789ABCDEFGHJKLMNPQRSTUVWXY";
//        char[] chars = strPool.toCharArray();
//        Random random = new SecureRandom();
//        StringBuilder res;
//        res = new StringBuilder();
//        while (res.length() < 23) {
//            final int index = random.nextInt(chars.length);
//            res.append(chars[index]);
//        }
//        System.out.println(res.toString());
//        System.out.println(verifyId(res.toString()));

        System.out.println(verifyId("98DPNGB8EU1T6UD808MHT6B"));

    }

    // 生成校验码
    public static String verifyId(String id) {
        int count = 0;
        byte[] bytes = id.getBytes();

        for (int i = 0; i < bytes.length; i++) {
            int n = Integer.parseInt(bytes[i]%11 + "");
            count += n * (Math.pow(2, (23 - i)) % 11);
        }

        switch (count % 11) {
            case 0: return "1";
            case 1: return "0";
            case 2: return "X";
            default:
                return 12 - (count % 11) + "";
        }

    }

}
