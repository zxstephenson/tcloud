package com.cloud.security.mapper;

import java.util.List;

import com.cloud.security.bean.PermissionRoleInfo;
import com.cloud.security.bean.Role;
import com.cloud.security.bean.UserInfo;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2019年2月26日
 */

public interface UserMapper
{
    public UserInfo findUserByName(String username);
    
    public List<Role> findRolesByUserId(String userId);
    
    public void addUser(UserInfo userInfo);
    
    public List<PermissionRoleInfo> queryAllPermissionRoleInfo();
    
    
}
