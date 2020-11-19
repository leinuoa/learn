package com.leinuoa.learn.shardingjdbc.dao;

import com.leinuoa.learn.shardingjdbc.entity.OrderItem;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderItemDao {
    Long insert(OrderItem model);
}
