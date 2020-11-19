package com.leinuoa.learn.shardingjdbc.Service.impl;

import com.leinuoa.learn.shardingjdbc.Service.DemoService;
import com.leinuoa.learn.shardingjdbc.dao.OrderDao;
import com.leinuoa.learn.shardingjdbc.dao.OrderItemDao;
import com.leinuoa.learn.shardingjdbc.entity.Order;
import com.leinuoa.learn.shardingjdbc.entity.OrderItem;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class DemoServiceImpl extends SqlSessionDaoSupport implements DemoService {
    @Resource
    private OrderDao orderDao;
    @Resource
    private OrderItemDao orderItemDao;


    @Override
    @Resource
    public void setSqlSessionFactory(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory){
        super.setSqlSessionFactory(sqlSessionFactory);
    }

    @Override
    public String insert(Integer userId) {
        Order order = new Order();
        order.setUserId(userId);
        order.setStatus("INSERT_TEST");
        orderDao.insert(order);

        long orderId = order.getOrderId();
        OrderItem item = new OrderItem();
        item.setOrderId(orderId);
        item.setUserId(userId);
        item.setStatus("INSERT_TEST");
        orderItemDao.insert(item);

        return orderId + "|" + item.getOrderItemId();
    }
}
