package com.cloud.service;

import java.util.List;
import java.util.Map;

import com.cloud.common.bean.User;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年10月25日
 */

public interface UserService
{
    public String getUsername(int id, List<String> list);
    
    public User createUser(int id, int age, Map<String, Object> feature, Map<String, Object> map);
    
    public void test01();
}
