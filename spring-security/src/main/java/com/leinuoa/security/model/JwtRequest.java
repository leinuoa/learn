package com.leinuoa.security.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 存储客户端发来的用户名密码
 */
@Data
public class JwtRequest implements Serializable {
    private static final long serialVersionUID = 16275924036767017L;
    private String username;
    private String password;
}
