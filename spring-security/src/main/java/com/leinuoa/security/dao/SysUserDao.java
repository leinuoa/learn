package com.leinuoa.security.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.leinuoa.security.model.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SysUserDao extends BaseMapper<SysUser> {

    @Select("select name as username,password from sys_user where name = #{username}")
    SysUser findByUsername(String username);

    SysUser save(SysUser user);
}
