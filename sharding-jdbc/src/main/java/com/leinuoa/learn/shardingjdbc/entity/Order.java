package com.leinuoa.learn.shardingjdbc.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Order implements Serializable {
    private static final long serialVersionUID = -1421703755919327899L;
    private long orderId;

    private int userId;

    private String status;
}
