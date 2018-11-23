package com.cloud.shiro.mapper;

import com.cloud.shiro.domain.Permission;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年11月23日
 */

public interface PermissionMapper
{

    public Permission queryPermissionByRoleId(String roleId);
}
