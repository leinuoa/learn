package com.leinuoa.security;

import com.leinuoa.security.dao.SysUserDao;
import com.leinuoa.security.model.SysUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringSecurityApplicationTests {

    @Autowired
    private SysUserDao sysUserDao;

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        SysUser user = sysUserDao.findByUsername("test");
        System.out.println(user);
    }

}
