package com.cloud.bus.consumer.channels;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年9月25日
 */

public interface InputChannel
{
    public static final String BROADCAST_INPUT = "broadcastInput";
    
    public static final String GROUPMESSAGE_INPUT = "groupMessageInput";
    
    @Input(BROADCAST_INPUT)
    SubscribableChannel broadcastInput();
    
    @Input(GROUPMESSAGE_INPUT)
    SubscribableChannel groupMessageInput();
}
