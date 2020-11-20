package com.leinuoa.security.model;

import java.io.Serializable;

/**
 * 响应客户端的token
 */
public class JwtResponse implements Serializable {
    private static final long serialVersionUID = 6691615200304872051L;
    private final String jwtToken;

    public JwtResponse(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getJwtToken() {
        return jwtToken;
    }
}
