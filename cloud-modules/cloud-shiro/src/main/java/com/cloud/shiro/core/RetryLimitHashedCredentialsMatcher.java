package com.cloud.shiro.core;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.springframework.beans.factory.annotation.Autowired;

import com.cloud.common.cache.cacheL2.CacheL2DAO;
import com.cloud.shiro.config.ShiroProperties;

/**
 * 对密码输入次数的限制，密码输入错误次数超过指定时，将禁止继续登录验证
 * 防止暴力破解
 * @author    zhangxin4
 * @version   3.1.0 2018年11月28日
 */

public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher 
{
    private static final String CREDENTIALS_MATCHER_PREFIX = "cloud:shiro:credentials_matcher_prefix";

    @Autowired
    private ShiroProperties shiroProperties;
    
    @Autowired
    private CacheL2DAO cacheL2DAO; 
    
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token,
            AuthenticationInfo info)
    {

        int maxRetryCount = shiroProperties.getMaxRetryCount(); //最大重试次数

        String username = (String)token.getPrincipal();
        AtomicInteger retryCount = new AtomicInteger(0);
        Object object = cacheL2DAO.get(getCacheKey(username));
        if(null != object)
        {
            retryCount = (AtomicInteger)object;
        }else 
        {
            cacheL2DAO.setex(getCacheKey(username), retryCount, shiroProperties.getLimitRetryTimeout());
        }
        
        if(retryCount.incrementAndGet() > maxRetryCount)
        {
            throw new ExcessiveAttemptsException("超过最大次数");
        }
        boolean isMatch = super.doCredentialsMatch(token, info);
        if(isMatch) //如果匹配
        {
            //删除缓存中对应的记录
            cacheL2DAO.del(getCacheKey(username));
        }else {
            //不匹配的情况，缓存中数据次数加1
            cacheL2DAO.set(getCacheKey(username), retryCount);
        }
        
        return isMatch;
    }
    
    private String getCacheKey(String username){
        return CREDENTIALS_MATCHER_PREFIX + ":" + username;
    }
}
