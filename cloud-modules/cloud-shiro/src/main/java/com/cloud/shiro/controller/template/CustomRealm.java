/**
 * <p>文件名称: CustomRealm.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.shiro.controller.template;

import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * 定义抽象类CustomRealm,可以直接继承该Realm
 * 
 * @author zhangxin4
 * @version   2.4.2 2018年8月31日
 */
public abstract class CustomRealm extends AuthorizingRealm
{
    /**
     * 更新用户授权信息缓存
     * 
     * @param principals
     */
    public void clearCachedAuthorizationInfo(PrincipalCollection principals)
    {
        super.clearCachedAuthorizationInfo(principals);
    }

    /**
     * 更新用户信息缓存.
     */
    public void clearCachedAuthenticationInfo(PrincipalCollection principals)
    {
        super.clearCachedAuthenticationInfo(principals);
    }

    /**
     * 清除用户授权信息缓存.
     */
    public void clearAllCachedAuthorizationInfo()
    {
        getAuthorizationCache().clear();
    }

    /**
     * 清除用户信息缓存.
     */
    public void clearAllCachedAuthenticationInfo()
    {
        getAuthenticationCache().clear();
    }

    /**
     * 清空所有缓存
     */
    public void clearCache(PrincipalCollection principals)
    {
        super.clearCache(principals);
    }

    /**
     * 清空所有认证缓存
     */
    public void clearAllCache()
    {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }

}
