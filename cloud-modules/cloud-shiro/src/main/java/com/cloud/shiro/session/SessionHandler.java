/**
 * <p>文件名称: SessionUtil.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.shiro.session;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;
import org.springframework.util.StringUtils;

import com.szkingdom.jros.shiro.cache.CacheHandler;

/**
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author zhangxin4
 * @version 3.1.0 2018年9月6日
 */
@Component
public class SessionHandler
{
    private static final Log logger = LogFactory.getLog(SessionHandler.class);

    /**
     * 当前所采用的模式：
     * false表示采用的shiro管理session；
     * true表示采用token的方式，使用缓存管理用户的信息
     */
    @Value("${isTokenModel:false}")
    private boolean isTokenModel;

    /**
     * session过期时间，单位秒，默认30分钟
     */
    @Value("${sessionExpireSeconds:1800}")
    private int sessionExpireSeconds;
    
    private static final String TOKEN_MODEL_REDIS_USERS_SESSION = "tokenModel:redis:users:session";
    
    /**
     * 对于基于token的模式，使用redis中的hash来存储，该属性指定hash的key
     */
    @Value("${tokenModelRedisUsersSessionKey:"+TOKEN_MODEL_REDIS_USERS_SESSION+"}")
    private String tokenModelRedisUsersSessionKey;
    
    
    @Autowired
    private CacheHandler cacheHandler;
    
    /**
     * 默认需要使用用户的token值作为前缀存储用户信息，
     * 如果为初始化token信息，则使用name值作为key
     * 存储在缓存中
     * @param name
     * @return
     */
    private byte[] getKey(String name)
    {
        return name.getBytes();
    }

    private Session getSession()
    {
        Subject subject = SecurityUtils.getSubject();
        return subject.getSession();
    }
    
    /**
     * 设置属性值
     * 
     * @param name
     * @param value
     */
    public void setAttribute(String name, Object value)
    {
        if (StringUtils.isEmpty(name))
        {
            logger.error("属性名不能为空。");
            return;
        }
        if (value == null)
        {
            logger.error("属性值不能为空。");
            return;
        }
        
        // 如果是采用的token模式则将key-value设置到redis中进行存储，否则将key-value设置到session中存储
        if (isTokenModel)
        {
            byte[] key = getKey(tokenModelRedisUsersSessionKey);
            byte[] field = getKey(name);
            //如果session过期时间小于等于0，则表示session不过期
            if(sessionExpireSeconds <= 0) 
            {
            	cacheHandler.hset(key, field, SerializationUtils.serialize(value));
            }else 
            {
            	cacheHandler.hsetex(key, field, SerializationUtils.serialize(value), sessionExpireSeconds);
            }
        } else
        {
            Session session = getSession();
            session.setAttribute(name, value);
        }
    }

    
    public void setAttribute(String token, String name, Object value)
    {
        setAttribute(token + ":" + name , value);
    }
    
    /**
     * 获取name对应的属性值
     * 
     * @param name
     * @return
     */
    public Object getAttribute(String name)
    {
        if (StringUtils.isEmpty(name))
        {
            logger.error("属性名不能为空");
            return null;
        }

        if (isTokenModel)
        {
        	byte[] key = getKey(tokenModelRedisUsersSessionKey);
            byte[] field = getKey(name);
            byte[] obj = null;
            //如果session过期时间小于等于0，则表示session不过期
            if(sessionExpireSeconds <= 0) 
            {
            	obj = cacheHandler.hget(key, field);
            }else 
            {
            	obj = cacheHandler.hgetex(key, field, sessionExpireSeconds); 
            }
            
            if(obj != null) 
        	{
        		return SerializationUtils.deserialize(obj);
        	}
            return obj;
        } else
        {
            Session session = getSession();
            return session.getAttribute(name);
        }
    }
    
    public Object getAttribute(String token, String name)
    {
        return getAttribute(token + ":" + name);
    }

    /**
     * 删除属性值
     * 
     * @param name
     */
    public void removeAttribute(String name)
    {
        if (StringUtils.isEmpty(name))
        {
            logger.error("key值不能为空");
            return;
        }

        if (isTokenModel)
        {
            byte[] key = getKey(name);
            cacheHandler.del(key);
        } else
        {
            Session session = getSession();
            session.removeAttribute(name);
        }

    }
}
