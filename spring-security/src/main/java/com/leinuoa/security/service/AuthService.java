package com.leinuoa.security.service;

import com.leinuoa.security.model.SysUser;

public interface AuthService {
    SysUser register(SysUser userToAdd);

    String login(String username, String password);

    String refresh(String oldToken);
}
