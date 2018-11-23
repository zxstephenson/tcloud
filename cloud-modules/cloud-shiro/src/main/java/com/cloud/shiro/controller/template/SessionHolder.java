/**
 * <p>文件名称: SessionHolder.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.shiro.controller.template;

import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 对外提供方法实现，用户认证、授权信息的清除
 * 
 * @author zhangxin4
 * @version   2.4.2 2018年8月31日
 */
@Component
public class SessionHolder
{/*
    @Autowired
    private CustomRealm customerRealm;

    *//**
     * 更新用户授权信息缓存
     * 
     * @param principals
     *//*
    public void clearCachedAuthorizationInfo(PrincipalCollection principals)
    {
        System.out.println(
                "==============clearCachedAuthorizationInfo===========");
        customerRealm.clearCachedAuthorizationInfo(principals);
    }

    *//**
     * 更新用户信息缓存.
     *//*
    public void clearCachedAuthenticationInfo(PrincipalCollection principals)
    {
        System.out.println(
                "==============clearCachedAuthenticationInfo===========");
        customerRealm.clearCachedAuthenticationInfo(principals);
    }

    *//**
     * 清除用户授权信息缓存.
     *//*
    public void clearAllCachedAuthorizationInfo()
    {
        System.out.println(
                "==============clearAllCachedAuthorizationInfo===========");
        customerRealm.clearAllCachedAuthorizationInfo();
    }

    *//**
     * 清除用户信息缓存.
     *//*
    public void clearAllCachedAuthenticationInfo()
    {
        System.out.println(
                "==============clearAllCachedAuthenticationInfo===========");
        customerRealm.clearAllCachedAuthenticationInfo();
    }

    *//**
     * 清空所有缓存
     *//*
    public void clearCache(PrincipalCollection principals)
    {
        System.out.println("==============clearCache===========");
        customerRealm.clearCache(principals);
    }

    *//**
     * 清空所有认证缓存
     *//*
    public void clearAllCache()
    {
        System.out.println("==============clearAllCache===========");
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }

*/}
