package com.cloud.security.bean;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2019年2月26日
 */

public class UserInfo
{
    private String userId;
    
    private String username;
    
    private String password;
    
    private String credentialSalt;

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getCredentialSalt()
    {
        return credentialSalt;
    }

    public void setCredentialSalt(String credentialSalt)
    {
        this.credentialSalt = credentialSalt;
    }

    @Override
    public String toString()
    {
        return "UserInfo [userId=" + userId + ", username=" + username
                + ", password=" + password + ", credentialSalt="
                + credentialSalt + "]";
    }
    
}
