package com.leinuoa.learn.shardingjdbc.dao;


import com.leinuoa.learn.shardingjdbc.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface OrderDao {
    Long insert(Order model);
}
