package com.leinuoa.learn.shardingjdbc.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderItem implements Serializable {
    private static final long serialVersionUID = -362293708528170252L;

    private long orderItemId;

    private long orderId;

    private int userId;

    private String status;

}
