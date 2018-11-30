package com.cloud.shiro.session;

import java.io.Serializable;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.SerializationUtils;

import com.cloud.common.cache.cacheL2.CacheL2DAO;
import com.cloud.shiro.config.ShiroProperties;

/**
 * redis实现共享session
 */
public class RedisSessionDAO extends EnterpriseCacheSessionDAO {

    private static Logger logger = LoggerFactory.getLogger(RedisSessionDAO.class);

    private static final String SHIRO_SESSION_PREFIX = "cloud:shiro:session";

    @Autowired
    private CacheL2DAO cacheL2Dao;
    
    @Autowired
    private ShiroProperties shiroProperties;
    
    private String getKey(String sessionId)
    {
        return SHIRO_SESSION_PREFIX + ":" + sessionId;            
    }
    
    private void saveSession(Session session)
    {
        if(session != null && session.getId() != null)
        {
            String key = getKey(session.getId().toString());
            byte[] value = SerializationUtils.serialize(session);
            int expireTime = shiroProperties.getSessionExpireTimeout();
            if(expireTime <= 0)
            {
                cacheL2Dao.set(key, value);
            }else 
            {                
                cacheL2Dao.setex(key, value, expireTime);
            }
        }
    }
    
    /**
     * 创建session，保存到数据库
     */
    @Override
    protected Serializable doCreate(Session session) {
        logger.error("=======doCreate===========");
        Serializable sessionId = super.doCreate(session);
        logger.debug("创建session:{}", session.getId());
        saveSession(session);
        return sessionId;
    }

    /**
     * 获取session
     */
    @Override
    protected Session doReadSession(Serializable sessionId) {
        logger.error("=======doReadSession===========");
        logger.debug("获取session:{}", sessionId);
        // 先从缓存中获取session，如果没有再去数据库中获取
        if(sessionId == null){
            return null;
        }
        String key = getKey(sessionId.toString());
        Session session = (Session)SerializationUtils.deserialize(cacheL2Dao.getByteValue(key));
        return session;
        
    }

    /**
     *  更新session
     */
    @Override
    protected void doUpdate(Session session) {
        logger.error("=======doUpdate===========");
        logger.debug("获取session:{}", session.getId());
        saveSession(session);
    }

    /**
     * 删除session
     */
    @Override
    protected void doDelete(Session session) {
        logger.error("=======doDelete===========");
        if(session == null || session.getId() == null){
            return ;
        }
        logger.debug("删除session:{}", session.getId());
        
        String key = getKey(session.getId().toString());
        cacheL2Dao.del(key);
        
    }
}