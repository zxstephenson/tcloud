package com.cloud.listener;

import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import com.cloud.common.bus.listeners.AbstractMessageListener;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年9月25日
 */
@Component
public class OtherMessageListener extends AbstractMessageListener
{
    @Override
    public void receiveBroadcastMessage(Message<Object> message)
    {
        System.err.println("==========OtherMessageListener=====receiveBroadcastMessage===========" + message);
    }

    @Override
    public void receiveGroupMessage(Message<Object> message)
    {
        System.err.println("==========OtherMessageListener=====receiveGroupMessage===========" + message);
    }
}
