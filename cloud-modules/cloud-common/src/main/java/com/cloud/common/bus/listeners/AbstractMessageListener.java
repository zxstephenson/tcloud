package com.cloud.common.bus.listeners;

import org.springframework.messaging.Message;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年9月25日
 */

public abstract class AbstractMessageListener implements MessageListener
{

    @Override
    public void receiveBroadcastMessage(Message<Object> message)
    {

    }

    @Override
    public void receiveGroupMessage(Message<Object> message)
    {

    }

}
