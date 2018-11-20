package com.cloud.security.service;

import java.util.Arrays;

import org.springframework.stereotype.Repository;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年11月12日
 */
@Repository
public class UserDao
{
    private SysRole admin = new SysRole("ADMIN", "管理员");
    
    private SysRole developer = new SysRole("DEVELOPER", "开发者");
    
    {
        SysPermission p1 = new SysPermission();
        p1.setCode("UserIndex");
        p1.setDesc("个人中心");
        p1.setUrl("/user/index.html");
        
        SysPermission p2 = new SysPermission();
        p2.setCode("BookList");
        p2.setDesc("图书列表");
        p2.setUrl("/book/list");
       
        SysPermission p3 = new SysPermission();
        p3.setCode("BookAdd");
        p3.setDesc("添加图书");
        p3.setUrl("/book/add");
        
        SysPermission p4 = new SysPermission();
        p4.setCode("BookDetail");
        p4.setDesc("查看图书");
        p4.setUrl("/book/detail");
       
        admin.setPermissionList(Arrays.asList(p1, p2, p3, p4));
        developer.setPermissionList(Arrays.asList(p1, p2));
    }
    
    public SysUser selectByName(String username){
        
        
        return new SysUser();
    }
    
}
