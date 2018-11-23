/**
 * <p>文件名称: RedisSessionManager.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.shiro.session;

import java.io.Serializable;

import javax.servlet.ServletRequest;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.session.mgt.WebSessionKey;

/**
 * 
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   2.4.2 2018年8月31日
 */
public class RedisSessionManager extends DefaultWebSessionManager
{
    /**
     * 读取session，首先默认从Request作用域中获取Session信息，如果没有获取到则从Redis缓存
     * 中获取Session信息
     */
    @Override
    protected Session retrieveSession(SessionKey sessionKey)
            throws UnknownSessionException
    {

        Serializable sessionId = getSessionId(sessionKey);

        ServletRequest request = null;
        if (sessionKey instanceof WebSessionKey)
        {
            WebSessionKey webSessionKey = (WebSessionKey) sessionKey;
            request = webSessionKey.getServletRequest();
        }

        if (request != null && sessionId != null)
        {
            Session session = (Session) request
                    .getAttribute(sessionId.toString());
            if (session != null)
            {
                return session;
            }
        }

        Session session = super.retrieveSession(sessionKey);
        if (request != null && sessionId != null)
        {
            request.setAttribute(sessionId.toString(), session);
        }

        return session;
    }

}
