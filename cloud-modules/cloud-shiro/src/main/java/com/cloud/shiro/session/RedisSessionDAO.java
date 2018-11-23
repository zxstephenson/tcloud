/**
 * <p>文件名称: RedisSessionDao.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.shiro.session;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.SerializationUtils;

import com.cloud.common.cache.cacheL2.CacheL2Dao;

/**
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author zhangxin4
 * @version   2.4.2 2018年8月31日
 */
public class RedisSessionDAO extends AbstractSessionDAO
{
    @Autowired
    private CacheL2Dao cacheL2Client;

    /**
     * 保存到redis中的session的默认失效时间，单位秒,默认30分钟
     */
    private int sessionExpireSeconds = 1800;

    /**
     * @param sessionExpireSeconds
     *            the sessionExpireSeconds to set
     */
    public void setSessionExpireSeconds(int sessionExpireSeconds)
    {
        this.sessionExpireSeconds = sessionExpireSeconds;
    }

    private static final String SHIRO_SESSION_REDIS = "shiro:session:redis";

    private byte[] getKey(String sessionId)
    {
        return (SHIRO_SESSION_REDIS + ":" + sessionId).getBytes();
    }

    private void saveSession(Session session)
    {
        if (session != null && session.getId() != null)
        {

            byte[] key = getKey(session.getId().toString());
            byte[] value = SerializationUtils.serialize(session);
            if(sessionExpireSeconds <= 0) 
            {
            	cacheL2Client.set(key, value);
            }else 
            {
            	cacheL2Client.setex(key, value, sessionExpireSeconds);
            }
        }
    }

    @Override
    protected Serializable doCreate(Session session)
    {
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session, sessionId);
        saveSession(session);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId)
    {
        Session session = (Session) SerializationUtils
                .deserialize(cacheL2Client.get(getKey(sessionId.toString())));

        return session;
    }

    @Override
    public void update(Session session) throws UnknownSessionException
    {
        saveSession(session);
    }

    @Override
    public void delete(Session session)
    {
        if (session != null && session.getId() != null)
        {
            cacheL2Client.del(getKey(session.getId().toString()));
        }
    }

    @Override
    public Collection<Session> getActiveSessions()
    {
        Set<byte[]> keys = cacheL2Client.keys(SHIRO_SESSION_REDIS.getBytes());

        Set<Session> sessions = new HashSet<>();

        if (CollectionUtils.isEmpty(keys))
        {
            return sessions;
        }

        for (byte[] key : keys)
        {
            Session session = (Session) SerializationUtils
                    .deserialize(cacheL2Client.get(getKey(key.toString())));
            if (session != null)
            {
                sessions.add(session);
            }
        }

        return sessions;
    }

}
