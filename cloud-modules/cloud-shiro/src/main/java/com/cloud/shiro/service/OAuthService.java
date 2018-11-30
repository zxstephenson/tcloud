package com.cloud.shiro.service;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年11月28日
 */

public interface OAuthService
{
    // 添加 auth code
    public void addAuthCode(String authCode, String username);
    
    // 添加 access token
    public void addAccessToken(String accessToken, String username); 
    
    // 验证 auth code 是否有效
    boolean checkAuthCode(String authCode); 
    
    // 验证 access token 是否有效
    boolean checkAccessToken(String accessToken); 
    
    // 根据 auth code 获取用户名
    String getUsernameByAuthCode(String authCode);
    
    // 根据 access token 获取用户名
    String getUsernameByAccessToken(String accessToken);
    
    //auth code / access token 过期时间
    long getExpireIn();
    
    // 检查客户端 id 是否存在
    public boolean checkClientId(String clientId);
    
    // 坚持客户端安全 KEY 是否存在
    public boolean checkClientSecret(String clientSecret);
}
